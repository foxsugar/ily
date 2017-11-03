import datetime
import json
import uuid

from django.core.cache import cache
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from summer_admin.apps.models import *
from summer_admin.rpc.rpc import *

TIME_OUT = 60 * 60 * 2


def check_login(func):
    """
    检测登录装饰器
    """

    def wrapper(req):
        print(req)
        x_token = req.META['HTTP_X_TOKEN']
        print(x_token)
        agent = cache.get(x_token)
        if agent is None:
            return JsonResponse({'code': 50014, 'message': '请登录'})
        else:
            return func(req)

    return wrapper


@csrf_exempt
def login(request):
    # test_mongo()
    """A view of all bands."""
    data = json.loads(request.body.decode())
    # print(dict(request.body.decode()))
    username = data['username']
    password = data['password']
    users = Agent_user.objects.filter(username=username).filter(password=password)
    if users.values().count() > 0:
        user = users.values()[0]
        # 放入缓存
        user_cache = {'id': user['id'], 'level': user['level'], 'username': user['username']}
        token = uuid.uuid4().hex
        cache.set(token, user_cache, TIME_OUT)
        result = {'code': 20000, 'data': {'token': token}}
        return JsonResponse(result)
    else:
        return JsonResponse({'code': 2000, 'data': '账户密码错误'})


@check_login
def get_info(request):
    """获得用户信息"""

    x_token = request.META['HTTP_X_TOKEN']
    print(x_token)
    dict = cache.get(x_token)
    level = dict["level"]
    agent_id = dict['id']
    username = dict['username']

    roles = None
    if username == 'admin':
        roles = ['admin']
    else:
        roles = ['delegate']

    array = Agent_user.objects.filter(id=agent_id)
    au = array[0]

    data = {'name': username, 'role': roles, 'money': au.money,
            'avatar': 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'}
    return JsonResponse({'code': 20000, 'data': data})


@check_login
def agent_list(request):
    """代理列表"""
    page = int(str(request.GET['page']))
    size = int(str(request.GET['size']))
    index_left = (page - 1) * size
    index_right = page * size

    # table_data = list(Agent_user.objects.values()[index_left:index_right])

    x_token = request.META['HTTP_X_TOKEN']
    print(x_token)
    dict = cache.get(x_token)
    level = dict["level"]
    agent_id = dict['id']
    agent_name = dict['username']

    array = None
    if agent_name == 'admin':
        array = Agent_user.objects.all()
    else:
        array = Agent_user.objects.filter(parent_id=agent_id)
    # array = Agent_user.objects.filter((Agent_user(parent_id=agent_id) | Agent_user(id=a)))
    table_data = list(array.values()[index_left:index_right])
    total_page = len(table_data)

    td = []
    for t in table_data:
        td.append(agent2vo(t))

    total_page = Agent_user.objects.count()

    data = {'tableData': td, 'totalPage': total_page}

    return JsonResponse({'code': 20000, 'data': data})


@check_login
def agent(request):
    param = json.loads(str(request.GET['agentForm']))
    method = request.method

    # 添加代理
    if method == "POST":
        create_agent_user(param, request)

        print("--")
        return JsonResponse({'code': 20000, 'data': param})


@check_login
def agent_charge(request):
    x_token = request.META['HTTP_X_TOKEN']
    dict = cache.get(x_token)
    slf_name = dict["username"]
    slf_id = dict["id"]

    array = Agent_user.objects.filter(id=slf_id)
    t_data = list(array.all())
    agent_user = t_data[0]

    """代理充值"""
    param = json.loads(str(request.GET['chargeForm']))
    id = param['id']
    num = param['num']

    if agent_user.money < num:
        return JsonResponse({'code': 100, 'data': '充值失败'})

    agent = Agent_user.objects.get(id=id)
    agent.money += num
    agent.save()

    if slf_name != 'admin':
        agent_user.money -= num
        agent_user.save()

    # int
    # WX = 1;
    # int
    # ZFB = 2;
    # int
    # SHARE = 3;
    # int
    # CHARGE_CARD = 4;
    # int
    # BIND_REFERRER = 5;
    # int
    # AGENT = 7;



    level = dict["level"]
    agent_id = dict['id']
    agent_charge = Agent_charge()
    agent_charge.agent_id = id
    agent_charge.charge_src_agent = agent_id
    agent_charge.charge_num = num
    agent_charge.charge_type = 7
    agent_charge.save()

    return JsonResponse({'code': 20000, 'data': agent.money})


@check_login
def constant(request):
    con = Constant.objects.filter(id=1).values()[0]

    return JsonResponse({'code': 20000, 'data': con})


@check_login
def constant_update(request):
    param = json.loads(str(request.GET['constantForm']))
    constant = Constant.objects.get(id=1)
    constant.init_money = param['init_money']
    constant.apple_check = param['apple_check']
    constant.download = param['download']
    constant.marquee = param['marquee']
    constant.version_of_android = param['version_of_android']
    constant.version_of_ios = param['version_of_ios']
    constant.apple_check = param['apple_check']
    constant.save()

    # 刷新游戏服务器数据
    client = get_client()
    # 调用这个是为了刷新服务器内存
    client.getBlackList()
    # client.modifyAndroidVersion(constant.version_of_android)
    return JsonResponse({'code': 20000, 'data': 'ok'})


def product_list(request):
    """代理列表"""
    page = int(str(request.GET['page']))
    size = int(str(request.GET['size']))
    index_left = (page - 1) * size
    index_right = page * size
    #
    table_data = list(Product.objects.values()[index_left:index_right])
    td = []
    for t in table_data:
        d = dict(t)
        # 日期转换
        d['create_time'] = str(t['create_time']).split('.')[0]
        td.append(d)

    total_page = Product.objects.count()

    data = {'tableData': td, 'totalPage': total_page}

    return JsonResponse({'code': 20000, 'data': data})


def product(request):
    """代理列表"""
    param = json.loads(str(request.GET['productForm']))
    method = request.method
    if method == 'POST':
        name = param['name']
        description = param['description']
        pd = Product()
        pd.name = name
        pd.description = description
        pd.code_id = param['code_id']
        pd.app_id = param['app_id']
        pd.channel_id = param['channel_id']
        pd.agent_id = param['agent_id']
        pd.create_time = datetime.datetime.now()
        print(pd.create_time)
        pd.save()
        data = {'msg': 'ok'}
        return JsonResponse({'code': 20000, 'data': data})
    if method == 'PUT':
        print('[[[[')


@check_login
def order_list(request):
    x_token = request.META['HTTP_X_TOKEN']
    user_cache = cache.get(x_token)
    slf_name = user_cache["username"]
    slf_id = user_cache["id"]

    """订单列表"""
    print("000")
    page = int(str(request.GET['page']))
    size = int(str(request.GET['size']))
    index_left = (page - 1) * size
    index_right = page * size
    #
    # 找到所有的app


    # if slf_name == 'admin':
    if slf_name == 'admin':
        values = Orders.objects.filter(status=1)
    else:
        app_id_list = 1
        #找到代理名下的app
        products = Product.objects.filter(agent_id=slf_id)
        app_id_list = list()
        for pro in list(products):
            app_index = str(pro.code_id) + "|" + str(pro.app_id) + "|" + pro.channel_id
            app_id_list.append(app_index)

        # print(app_id_list)
        values = Orders.objects.filter(app_index__in=app_id_list)
        # table_data = list(values)[index_left:index_right]
        # print(table_data)

    table_data = list(values.values())[index_left:index_right]

    td = []
    for t in table_data:
        d = dict(t)
        # 日期转换
        d['order_time'] = str(t['order_time']).split('.')[0]
        d['notify_time'] = str(t['notify_time']).split('.')[0]
        td.append(d)

    total_page = values.count()

    data = {'tableData': td, 'totalPage': total_page}

    return JsonResponse({'code': 20000, 'data': data})


def agent2vo(agent):
    """代理显示"""
    return {
        'id': agent['id'],
        'username': agent['username'],
        'password': agent['password'],
        'invite_code': agent['invite_code'],
        'realName': agent['real_name'],
        'level': agent['level'],
        'parentId': agent['parent_id'],
        'email': agent['email'],
        'createTime': agent['create_time'],
        'idCard': agent['id_card'],
        'cell': agent['cell'],
        'area': agent['area'],
        'address': agent['address'],
        'money': agent['money'],
        'gold': agent['gold'],
        'payDeduct': agent['pay_deduct'],
        'shareDeduct': agent['share_deduct'],
        'parentPayDeduct': agent['parent_pay_deduct'],
        'parentShareDeduct': agent['parent_share_deduct'],
    }


def create_agent_user(agent, request):
    x_token = request.META['HTTP_X_TOKEN']
    print(x_token)
    dict = cache.get(x_token)
    level = dict["level"]
    agent_id = dict['id']
    agent_name = dict['username']
    """创建代理"""
    user = Agent_user()
    data = agent
    user.username = data['username']
    user.password = data['password']
    user.invite_code = data['invite_code']
    user.real_name = data['realName']
    user.level = data['level']
    user.parent_id = agent_id
    user.email = data['email']
    user.create_time = datetime.datetime.now()
    user.id_card = data['idCard']
    user.cell = data['cell']
    user.area = data['area']
    user.address = data['address']
    user.money = 0
    user.gold = 0
    user.pay_deduct = data['payDeduct']
    user.share_deduct = data['shareDeduct']
    user.parent_pay_deduct = data['parentPayDeduct']
    user.parent_share_deduct = data['parentShareDeduct']
    # 保存
    user.save()

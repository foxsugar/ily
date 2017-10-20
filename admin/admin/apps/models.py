from django.db import models


class Agent_user(models.Model):
    id = models.AutoField('id', primary_key=True)
    username = models.CharField('姓名', max_length=255, default='')
    password = models.CharField('密码', max_length=200, default='')
    invite_code = models.CharField('邀请码', max_length=255, default='')
    real_name = models.CharField('真实姓名', max_length=255, default='')
    level = models.IntegerField('级别', default=0)
    parent_id = models.IntegerField('上级id', default=0)
    email = models.CharField('邮件', max_length=255, default='')
    create_time = models.DateTimeField('创建时间', auto_now=True)
    id_card = models.CharField('身份证', max_length=255, default='')
    cell = models.IntegerField('电话', default=0)
    area = models.CharField('所属区域', max_length=2000, default='')
    address = models.CharField('地址', max_length=2000, default='')
    money = models.FloatField('钱', default=0)
    gold = models.FloatField('点券', default=0)
    pay_deduct = models.FloatField('支付提成', default=0)
    share_deduct = models.FloatField('分享提成', default=0)
    parent_pay_deduct = models.FloatField('上级支付提成', default=0)
    parent_share_deduct = models.FloatField('上级分享提成', default=0)

    class Meta:
        db_table = 'agent_user'
        verbose_name = '代理'
        verbose_name_plural = '代理'
        managed = True


class Users(models.Model):
    id = models.AutoField(primary_key=True)
    account = models.CharField(max_length=255, default='')
    ali_id = models.CharField(max_length=255, default='')
    cash = models.FloatField(default=0)
    gold = models.FloatField(default=0)
    money = models.FloatField(default=0)
    rebate = models.FloatField(default=0)
    email = models.CharField(max_length=2000, default='')
    father_id = models.CharField(max_length=255, default='')
    image = models.CharField(max_length=4000, default='')
    ip_config = models.CharField(max_length=255, default='')
    open_id = models.CharField(max_length=255, default='')
    password = models.CharField(max_length=255, default='')
    username = models.CharField(max_length=4000, default='')
    uuid = models.CharField(max_length=255, default='')
    last_login_date = models.DateTimeField(auto_now=True)
    regist_date = models.DateTimeField(auto_now=True)
    referee = models.IntegerField(default=0)
    sex = models.IntegerField(default=0)
    vip = models.IntegerField(default=0)
    user_info = models.TextField(default='')


    class Meta:
        db_table = 'users'
        verbose_name = '用户表'
        verbose_name_plural = '用户'
        managed = False


class Order(models.Model):
    trade_id = models.CharField(primary_key=True,max_length=255)
    app_id = models.CharField(max_length=255, default='')
    channel_id = models.CharField(max_length=255, default='')
    cp_param = models.CharField(max_length=255, default='')
    imei = models.CharField(max_length=255, default='')
    imsi = models.CharField(max_length=255, default='')
    notify_time = models.DateTimeField(auto_now=True)
    order_time = models.DateTimeField(auto_now=True)
    op_type = models.CharField(max_length=255, default='')
    order_type = models.IntegerField(default=0)
    order_id = models.CharField(max_length=255, default='')
    sub_order_id = models.CharField(max_length=255, default='')
    user_id = models.CharField(max_length=255, default='')
    phone = models.CharField(max_length=255, default='')
    point_id = models.CharField(max_length=255, default='')
    province = models.CharField(max_length=255, default='')
    price = models.FloatField(default=0)
    status = models.IntegerField(default=0)

    class Meta:
        db_table = 'orders'
        verbose_name = '订单表'
        verbose_name_plural = '订单'
        managed = False


class Product(models.Model):
    id = models.AutoField('id', primary_key=True)
    name = models.CharField(max_length=255, default='')
    code_id = models.IntegerField(default=0)
    app_id = models.CharField(max_length=255, default='')
    channel_id = models.CharField(max_length=255, default='')
    description = models.CharField(max_length=4000, default='')
    create_time = models.DateTimeField(auto_now=True)
    status = models.IntegerField(default=0)

    class Meta:
        db_table = 'product'
        verbose_name = '产品表'
        verbose_name_plural = '产品'


class Constant(models.Model):
    id = models.AutoField(primary_key=True)
    init_money = models.IntegerField(default=0)
    share_money = models.IntegerField(default=0)
    black_list = models.TextField(default=0)
    apple_check = models.CharField(max_length=255, default='')
    download = models.CharField(max_length=255, default='')
    download2 = models.CharField(max_length=255, default='')
    marquee = models.CharField(max_length=255, default='')
    marquee1 = models.CharField(max_length=255, default='')
    marquee2 = models.CharField(max_length=255, default='')
    version_of_android = models.CharField(max_length=255, default='')
    version_of_ios = models.CharField(max_length=255, default='')

    class Meta:
        db_table = 'constant'
        verbose_name = '常量表'
        verbose_name_plural = '常量表'
        managed = False

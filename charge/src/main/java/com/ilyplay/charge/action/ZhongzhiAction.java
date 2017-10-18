package com.ilyplay.charge.action;

import com.ilyplay.charge.constant.ChargeType;
import com.ilyplay.charge.model.Order;
import com.ilyplay.charge.service.OrderService;
import com.ilyplay.charge.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxianping on 2017/9/28.
 */
@Controller
@EnableAutoConfiguration
public class ZhongzhiAction {

    private Logger logger = LoggerFactory.getLogger(ZhongzhiAction.class);

    private static final Map<Integer,String> provinceMap = new HashMap<>();

    static{
        provinceMap.put(10,"北京");
        provinceMap.put(11,"上海");
        provinceMap.put(12,"天津");
        provinceMap.put(13,"重庆");
        provinceMap.put(14,"河北");
        provinceMap.put(15,"山西");
        provinceMap.put(16,"河南");
        provinceMap.put(17,"辽宁");
        provinceMap.put(18,"吉林");
        provinceMap.put(19,"黑龙江");
        provinceMap.put(20,"内蒙古");
        provinceMap.put(21,"江苏");
        provinceMap.put(22,"山东");
        provinceMap.put(23,"安徽");
        provinceMap.put(24,"浙江");
        provinceMap.put(25,"福建");
        provinceMap.put(26,"湖北");
        provinceMap.put(27,"湖南");
        provinceMap.put(28,"广东");
        provinceMap.put(29,"广西");
        provinceMap.put(30,"江西");
        provinceMap.put(31,"四川");
        provinceMap.put(32,"海南");
        provinceMap.put(33,"贵州");
        provinceMap.put(34,"云南");
        provinceMap.put(35,"西藏");
        provinceMap.put(36,"陕西");
        provinceMap.put(37,"甘肃");
        provinceMap.put(38,"青海");
        provinceMap.put(39,"宁夏");
        provinceMap.put(40,"新疆");
    }

    @Autowired
    private OrderService orderService;


    @RequestMapping("/zhongzhi")
    @ResponseBody
    String charge(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try{

            String app_id = request.getParameter("app_id");
            String channel = request.getParameter("channel_id");
            //todo 文档 callback_args	渠道号（改）
            if (channel == null || "".equals(channel)) {
                channel = request.getParameter("callback_args");
            }
            String cpParam = request.getParameter("user_orderid");
            //1移动2联通3电信
            String op_type = request.getParameter("op_type");
            String orderDate = request.getParameter("order_date");
            long orderTime = Long.valueOf(request.getParameter("order_time"));
            String tradeId = request.getParameter("trade_id");
            double price = Double.parseDouble(request.getParameter("code_money"));
            String user_id = request.getParameter("user_id");
            String point_id = request.getParameter("point_id");
            int province = Integer.valueOf(request.getParameter("province_id"));

            //todo 没有专门的order_id 把tradeid 当orderid
            String order_id = request.getParameter("trade_id");


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(orderTime * 1000);
            Date date = calendar.getTime();

            Order order = new Order();
            order.setApp_id(app_id).
                    setChannel_id(channel).
                    setOrder_id(order_id).
                    setStatus(1).
                    setPrice(price).
                    setOrder_time(date).
                    setTrade_id(tradeId).
                    setOp_type(op_type).
                    setProvince(provinceMap.getOrDefault(province, "")).
                    setCp_param(cpParam).
                    setOrder_type(ChargeType.ZHONGZHI).
                    setNotify_time(new Date()).
                    setUser_id(user_id).
                    setPoint_id(point_id);

            order.setApp_index(StringUtil.getAppIndex("" + order.getOrder_type(), order.getApp_id(), order.getChannel_id()));
            orderService.save(order);

            result = "ok";
        }catch (Exception e){
            logger.error("zhongzhi notify error : ",e);
            result = "error";
        }


        return result;
    }

//    http://localhost:8999/zhongzhi?app_id=1001&callback_args=channel_test&callback_args=channel_test&user_orderid=20160101000001&code_money=2000&op_type=1&order_date=2016-12-07&order_id=1050587658&order_time=1481077428&point_id=20152&province_id=19&province_name=%E9%BB%91%E9%BE%99%E6%B1%9F&trade_id=105058765801&unique_order_id=2016120710234889682&user_id=50021

//             10: "北京",
//            11: "上海",
//            12: "天津",
//            13: "重庆",
//            14: "河北",
//            15: "山西",
//            16: "河南",
//            17: "辽宁",
//            18: "吉林",
//            19: "黑龙江",
//            20: "内蒙古",
//            21: "江苏",
//            22: "山东",
//            23: "安徽",
//            24: "浙江",
//            25: "福建",
//            26: "湖北",
//            27: "湖南",
//            28: "广东",
//            29: "广西",
//            30: "江西",
//            31: "四川",
//            32: "海南",
//            33: "贵州",
//            34: "云南",
//            35: "西藏",
//            36: "陕西",
//            37: "甘肃",
//            38: "青海",
//            39: "宁夏",
//            40: "新疆",

}

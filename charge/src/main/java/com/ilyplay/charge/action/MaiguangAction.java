package com.ilyplay.charge.action;

import com.ilyplay.charge.constant.ChargeType;
import com.ilyplay.charge.model.Order;
import com.ilyplay.charge.service.OrderService;
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
public class MaiguangAction {

    private static Map<Integer, String> provinceMap = new HashMap<>();
    static{
        provinceMap.put(1,"内蒙古");
        provinceMap.put(2,"贵州");
        provinceMap.put(3,"江苏");
        provinceMap.put(4,"安徽");
        provinceMap.put(5,"山东");
        provinceMap.put(6,"黑龙江");
        provinceMap.put(7,"陕西");
        provinceMap.put(8,"广东");
        provinceMap.put(9,"广西");
        provinceMap.put(10,"河南");
        provinceMap.put(11,"宁夏");
        provinceMap.put(12,"云南");
        provinceMap.put(13,"湖北");
        provinceMap.put(14,"西藏");
        provinceMap.put(15,"河北");
        provinceMap.put(16,"福建");
        provinceMap.put(17,"甘肃");
        provinceMap.put(18,"浙江");
        provinceMap.put(19,"湖南");
        provinceMap.put(20,"山西");
        provinceMap.put(21,"江西");
        provinceMap.put(22,"四川");
        provinceMap.put(23,"新疆");
        provinceMap.put(24,"吉林");
        provinceMap.put(25,"辽宁");
        provinceMap.put(26,"青海");
        //todo 没有27
        provinceMap.put(28,"上海");
        provinceMap.put(29,"海南");
        provinceMap.put(30,"北京");
        provinceMap.put(31,"天津");
        provinceMap.put(32,"重庆");
    }


    @Autowired
    private OrderService orderService;


    @RequestMapping("/maiguang")
    @ResponseBody
    Object charge(HttpServletRequest request, HttpServletResponse response) {

        String tradeId = request.getParameter("TransactionID");
        String sun_order_id = request.getParameter("SubTransactionID");
        // 1是成功
        int status = Integer.valueOf(request.getParameter("Status"));
        Double mo_price = Double.parseDouble(request.getParameter("TotalMoney"));
        Double valid_price = Double.parseDouble(request.getParameter("ValidMoney"));
        Double total_price = Double.parseDouble(request.getParameter("MoMoney"));
        String cpParam = request.getParameter("Ext");
        String order_id = request.getParameter("OID");
        String goods_id = request.getParameter("GoodsID");
        String channel = request.getParameter("ChannelID");
        long orderTime = Long.valueOf(request.getParameter("TransactionTime"));
        String ip = request.getParameter("IP");
        // 1移动 2联通 3电信
        String op_type = request.getParameter("Provider");
        String province = request.getParameter("Province");

        String sign = request.getParameter("sign");


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(orderTime);
        Date date = calendar.getTime();

        Order order = new Order();
        order.setChannel_id(channel).
                setOrder_id(order_id).
                setStatus(status).
                setPrice(total_price).
                setOrder_time(date).
                setTrade_id(tradeId).
                setOp_type(op_type).
                setProvince(provinceMap.getOrDefault(province, "")).
                setCp_param(cpParam).
                setOrder_type(ChargeType.MAIGUANG).
                setNotify_time(new Date()).
                setApp_id(goods_id);

        orderService.save(order);

        Map<String, Object> result = new HashMap<>();
        result.put("flag", 0);
        result.put("msg", "success");

        return result;
    }


    private static String getProvince(int code) {
        return provinceMap.getOrDefault(code, "");
    }
}

//http://localhost:8999/maiguang?ChannelID=mai001&Ext=0&GoodsID=30&IP=0&OID=1447840073915&Price=0&Provider=0&Province=0&Status=0&TransactionID=1324546576&TotalMoney=1&ValidMoney=1&MoMoney=1&TransactionTime=1000000000000

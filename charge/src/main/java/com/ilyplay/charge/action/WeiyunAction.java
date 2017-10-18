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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunxianping on 2017/9/28.
 */
@Controller
@EnableAutoConfiguration
public class WeiyunAction {

    private Logger logger = LoggerFactory.getLogger(WeiyunAction.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping("/weiyun")
    @ResponseBody
    String charge(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try {
            //todo ip白名单
            String app_id = request.getParameter("app_code");
            String channel = request.getParameter("channel");
            String imei = request.getParameter("imei");
            String imsi = request.getParameter("imsi");
            String mobile = request.getParameter("mobile");
            String order_id = request.getParameter("orderId");
            String orderTime = request.getParameter("orderTime");
            //todo 0是成功
            int status = Integer.valueOf(request.getParameter("feeResult"));
            // 和别的平台统一
            status = status == 0 ? 1 : 0;
            double price = Double.parseDouble(request.getParameter("price"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(orderTime, new ParsePosition(0));
            Order order = new Order();
            order.setApp_id(app_id).
                    setChannel_id(channel).
                    setImei(imei).
                    setImsi(imsi).
                    setPhone(mobile).
                    setOrder_id(order_id).
                    //order当流水id
                            setTrade_id(order_id).
                    setStatus(status).
                    setPrice(price).
                    setOrder_time(date).
                    setOrder_type(ChargeType.WEIYUN).
                    setNotify_time(new Date());
            order.setApp_index(StringUtil.getAppIndex("" + order.getOrder_type(), order.getApp_id(), order.getChannel_id()));

            orderService.save(order);

            result = "OK";
        } catch (Exception e) {
            logger.error("weiyun notify error : ", e);
            result = "ERROR";
        }

        return result;
    }

    //测试
    //http://localhost:8999/weiyun?app_code=12345678&channel=channel&imsi=0&imei=imei&mobile=13800138000&price=2000&orderId=orderId&orderTime=2017-06-13 11:02:18&feeResult=0

}

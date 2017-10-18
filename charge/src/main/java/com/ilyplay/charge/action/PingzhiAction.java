package com.ilyplay.charge.action;

import com.ilyplay.charge.config.ServerConfig;
import com.ilyplay.charge.constant.ChargeType;
import com.ilyplay.charge.model.Order;
import com.ilyplay.charge.service.OrderService;
import com.ilyplay.charge.utils.MD5Util;
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
public class PingzhiAction {
    private Logger logger = LoggerFactory.getLogger(PingzhiAction.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private ServerConfig serverConfig;

    @RequestMapping("/pingzhi")
    @ResponseBody
    String charge(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try {

            String tradeId = request.getParameter("OutTradeNo");
            String channel = request.getParameter("ChannelId");
            String order_id = request.getParameter("OrderId");
            String orderTime = request.getParameter("OrderTime");
            String cpParam = request.getParameter("CpParam");
            String sign = request.getParameter("Sign");
            // 0：移动，1：联通，2：电信
            String op_type = request.getParameter("CarrierType");
            //统一成 1移动 2联通 3电信
            int op = Integer.valueOf(op_type);
            op += 1;
            op_type = "" + op;

            String app_id = request.getParameter("AppId");
            String version = request.getParameter("Version");
            String sub_order_id = request.getParameter("SubIndex");
            //todo 对照表
            String province = request.getParameter("Province");
            // 1是成功
            int status = Integer.valueOf(request.getParameter("Status"));
            double price = Double.parseDouble(request.getParameter("Fee"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = dateFormat.parse(orderTime, new ParsePosition(0));
            Order order = new Order();
            order.setApp_id(app_id).
                    setChannel_id(channel).
                    setOrder_id(order_id).
                    setStatus(status).
                    setPrice(price).
                    setOrder_time(date).
                    setTrade_id(tradeId).
                    setOp_type(op_type).
                    setSub_order_id(sub_order_id).
                    setProvince(province).
//                setVersion(version).
                    setCp_param(cpParam).
                    setOrder_type(ChargeType.PINGZHI).
                    setNotify_time(new Date());

            //todo 签名验证
//        String md5 = getMD5(tradeId, orderTime, order_id, "" + status, "" + price, cpParam, serverConfig.getPingzhi_key());
//
//        logger.info("md5 = " + md5);
            order.setApp_index(StringUtil.getAppIndex("" + order.getOrder_type(), order.getApp_id(), order.getChannel_id()));
            orderService.save(order);
            result = "OK";
        } catch (Exception e) {
            logger.error("pingzhi notify error : ",e);
            result = "ERROR";
        }

        return result;
    }

    //    http://localhost:8999/pingzhi?OutTradeNo=PO201502271350478428&OrderTime=20150227135057&OrderId=4FF2F448CAB41D81&Status=1&Fee=1&CpParam=201502271350472521&Sign=f572792c5e1fff96688f87e8f1aa5036&ChannelId=1001&CarrierType=0&AppId=7&Version=1.1.6&SubIndex=1
    private String getMD5(String... str) {
        StringBuilder sb = new StringBuilder("");
        for (String s : str) {
            sb.append(s);
            sb.append("#");
        }
        sb.substring(0, sb.length() - 1);
        String s = sb.toString();
        return MD5Util.getMD5(s);

    }
}

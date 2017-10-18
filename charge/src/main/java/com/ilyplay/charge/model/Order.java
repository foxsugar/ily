package com.ilyplay.charge.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sunxianping on 2017/9/28.
 */
@DynamicUpdate
@Entity
@Table(name = "orders",
        indexes = {
                @Index(name = "order_type", columnList = "order_type"),
                @Index(name = "channel_id", columnList = "channel_id"),
        })
public class Order {

    @Id
    private String trade_id;//合作方唯一id

    private int order_type;//订单类型 四个同步厂商 微信支付宝等

    private String order_id;//订单号
    private String sub_order_id;//子订单号
    private String channel_id;//渠道
    private String app_id;//产品id
    private String user_id;//玩家id
    private String point_id;//计费点id
//    private String goods_id;//商品id
    private String op_type;//运营商类型
    private double price;//金额
    private Date order_time;//时间
    private int status;//状态
    private String province;//省份

    private String phone;//手机号
    private String imei;//imei
    private String imsi;//imsi
//    private String ip;//ip

    private String cp_param;//cp传过来的参数
//    private String version;//客户端版本

    private Date notify_time;//通知时间
    private String app_index;



    public int getOrder_type() {
        return order_type;
    }

    public Order setOrder_type(int order_type) {
        this.order_type = order_type;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public Order setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getSub_order_id() {
        return sub_order_id;
    }

    public Order setSub_order_id(String sub_order_id) {
        this.sub_order_id = sub_order_id;
        return this;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public Order setTrade_id(String trade_id) {
        this.trade_id = trade_id;
        return this;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public Order setChannel_id(String channel_id) {
        this.channel_id = channel_id;
        return this;
    }

    public String getApp_id() {
        return app_id;
    }

    public Order setApp_id(String app_id) {
        this.app_id = app_id;
        return this;
    }

    public String getUser_id() {
        return user_id;
    }

    public Order setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getPoint_id() {
        return point_id;
    }

    public Order setPoint_id(String point_id) {
        this.point_id = point_id;
        return this;
    }

    public String getOp_type() {
        return op_type;
    }

    public Order setOp_type(String op_type) {
        this.op_type = op_type;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Order setPrice(double price) {
        this.price = price;
        return this;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public Order setOrder_time(Date order_time) {
        this.order_time = order_time;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Order setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public Order setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public Order setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getImsi() {
        return imsi;
    }

    public Order setImsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    public String getCp_param() {
        return cp_param;
    }

    public Order setCp_param(String cp_param) {
        this.cp_param = cp_param;
        return this;
    }


    public Date getNotify_time() {
        return notify_time;
    }

    public Order setNotify_time(Date notify_time) {
        this.notify_time = notify_time;
        return this;
    }

    public String getApp_index() {
        return app_index;
    }

    public Order setApp_index(String app_index) {
        this.app_index = app_index;
        return this;
    }
}

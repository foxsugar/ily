package com.ilyplay.charge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by sunxianping on 2017/9/28.
 */
@ConfigurationProperties(prefix = "serverConfig")
public class ServerConfig {
    private String pingzhi_key = "test";


    public String getPingzhi_key() {
        return pingzhi_key;
    }

    public ServerConfig setPingzhi_key(String pingzhi_key) {
        this.pingzhi_key = pingzhi_key;
        return this;
    }
}

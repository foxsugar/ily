package com.ilyplay.charge.utils;

/**
 * Created by sunxianping on 2017/10/18.
 */
public class StringUtil {

    public static String getAppIndex(String type, String app, String channel){
        return type + "|" + app + "|" + channel;
    }
}

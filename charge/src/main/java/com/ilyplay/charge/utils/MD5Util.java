package com.ilyplay.charge.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sunxianping on 2017/9/29.
 */
public class MD5Util {

    public static String getMD5(String s) {
        String result = null;
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            result = base64en.encode(md5.digest(s.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;

    }
}

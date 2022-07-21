package com.example.campingrecord.utils;

import com.example.campingrecord.constant.Constants;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5Str(String strValue) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 加盐操作增加常量值
        return Base64.encodeBase64String(md5.digest((strValue + Constants.SALT).getBytes())) ;
    }

}

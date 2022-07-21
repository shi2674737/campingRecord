package com.example.campingrecord.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * jwt工具
 *
 * @author Administrator
 * @name JwtUtil
 * @date 2021-07-28 10:31
 */
public class JwtUtil {
    /**
     * 秘钥
     */
    private static String SECRET_KEY = "akcxakakc1";

    /**
     * 生成token  header.payload.sign
     */
    public static String getToken(Map<String, String> map) {
        //设置令牌过期时间 1天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return token;
    }

    /**
     * token验证 合法性
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

    /**
     * 获取token信息方法
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        return verify;
    }

    /**
     * 在token中获取到信息
     */
    public static String getString(String param, String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(param).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 判断是否过期
     */
    public static boolean isExpire(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().getTime() < System.currentTimeMillis() ;
    }
}

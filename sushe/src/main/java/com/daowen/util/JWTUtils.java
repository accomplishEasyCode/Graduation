package com.daowen.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

   private static String salt="abc***1&^";

    /**
     * 获取token
     *
     * @param map
     * @return
     */
    public static String getToken(Map<String,String > map){

        Calendar instance= Calendar.getInstance();
        instance.add(Calendar.DATE,7);
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String sign = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(salt));

        System.out.println("sign = " + sign);

        return sign;
    }

    /**
     * 验证
     * @param token
     */

    public static DecodedJWT verify(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(salt)).build();
        return verifier.verify(token);
    }



}

package com.nowander.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.nowander.common.security.JwtConfig;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wtk
 * @date 2021-10-28
 */
@Slf4j
public class TokenUtil {

    private static JwtConfig jwtConfig;
    static {
        SpringUtil.getBean(JwtConfig.class);
    }


    /**
     * 校验token是否过期
     * @param token
     * @return
     */
    public static boolean expiredToken(String token) {
        return System.currentTimeMillis() < getExpiredToken(token);
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    public static long getExpiredToken(String token) {
        return Long.parseLong(parseAndGet(token, JWT.EXPIRES_AT).toString());
    }

    /**
     * 解析jwt
     * @param token
     * @return
     */
    public static JSONObject parse(@NotNull String token) {
        return JWTUtil.parseToken(token).getPayload().getClaimsJson();
    }

    /**
     * 解析jwt，获取某个属性
     * @param token
     * @param name
     * @return
     */
    public static Object parseAndGet(String token, String name) {
        return JWTUtil.parseToken(token).getPayload().getClaim(name);
    }
}

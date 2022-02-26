package com.nowander.common.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.nowander.common.config.JwtConfig;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.enums.RedisKey;
import com.nowander.common.exception.TokenException;
import com.nowander.common.pojo.po.User;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Component
@AllArgsConstructor
public class TokenService {

    private JwtConfig jwtConfig;
    private RedisTemplate<String, String> redisTemplate;

    public String createToken(User user) {
        Map<String, Object> payload = new HashMap<>(8);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("avatar", user.getAvatar());
        return createToken(payload);
    }

    /**
     * 生成jwt
     * @param payload 数据主体
     * @return
     */
    public String createToken(Map<String, Object> payload) {
        // 每个jwt都默认生成一个到期时间
        payload.put("expire_time", System.currentTimeMillis() + jwtConfig.getExpireSeconds());
        // 生成私钥
        JWTSigner jwtSigner = JWTSignerUtil.hs256(jwtConfig.getKeyBytes());
        // 生成token
        return JWTUtil.createToken(payload, jwtSigner);
    }

    /**
     * 校验token是否有效，但不检验是否过期
     * @param token
     * @return
     */
    private boolean verifyToken(String token) {
        return JWTUtil.verify(token, jwtConfig.getKeyBytes());
    }

    /**
     * 校验token是否过期
     * @param token
     * @return
     */
    private boolean expiredToken(String token) {
        return System.currentTimeMillis() < getExpiredToken(token);
    }

    /**
     * 获取token过期时间
     * @param token
     * @return
     */
    private long getExpiredToken(String token) {
        return Long.parseLong(parseAndGet(token, "expire_time").toString());
    }

    /**
     * 解析jwt
     * @param token
     * @return
     */
    private JSONObject parse(@NotNull String token) {
        return JWTUtil.parseToken(token).getPayload().getClaimsJson();
    }

    /**
     * 解析jwt，获取某个属性
     * @param token
     * @param name
     * @return
     */
    private Object parseAndGet(String token, String name) {
        return JWTUtil.parseToken(token).getPayload().getClaim(name);
    }


    private boolean existToken(HttpServletRequest request) {
        return StrUtil.isBlank(getTokenStringWithoutVerify(request));
    }

    /**
     * 从request中获取token字符串，但没有任何验证
     * @param request
     * @return 可能为null
     */
    @Nullable
    public String getTokenStringWithoutVerify(HttpServletRequest request) {
        String authorization = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        int index = authorization.indexOf(jwtConfig.getTokenType());
        if (index == -1) {
            return null;
        }
        String token = authorization.substring(index + jwtConfig.getTokenTypeLength());
        return token.trim();
    }

    /**
     * 获取token并进行校验
     * @param request
     * @return
     * @throws TokenException 当token缺失、过期、非法时抛出
     */
    @NotNull
    public String requireVaildToken(HttpServletRequest request) {
        String token = getTokenStringWithoutVerify(request);
        requireVaildToken(token);
        return token;
    }

    @NotNull
    public void requireVaildToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new TokenException(ApiInfo.TOKEN_MISSING);
        }
        if (!verifyToken(token)) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        String username = (String) parseAndGet(token, "username");
        // 缓存中没有就说明token过期了
        if (redisTemplate.opsForValue().get(RedisKey.USER_TOKEN + username) == null) {
            throw new TokenException(ApiInfo.TOKEN_EXP);
        }
    }

    /**
     * 判断是否具有有效token
     * @param request
     * @return
     */
    @Nullable
    public boolean verifyTokenIfExist(HttpServletRequest request) {
        String token = getTokenStringWithoutVerify(request);
        if (token == null) {
            return false;
        }
        return verifyToken(token);
    }

    /**
     * 通过req获取JWT中包含的User信息
     * @param request
     * @return
     * @throws TokenException
     */
    public User requireUserByToken(HttpServletRequest request) throws TokenException {
        JSONObject tokenClaims = parse(requireVaildToken(request));
        return createUser(tokenClaims);
    }

    private User createUser(@NotNull JSONObject tokenClaims) {
        User user = new User();
        user.setId(tokenClaims.get("id", Integer.class));
        user.setEmail(tokenClaims.get("username", String.class));
        user.setAvatar(tokenClaims.get("avatar", String.class));
        return user;
    }
}

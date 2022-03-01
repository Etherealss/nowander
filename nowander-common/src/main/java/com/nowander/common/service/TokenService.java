package com.nowander.common.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.nowander.common.security.JwtConfig;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.enums.RedisKeyPrefix;
import com.nowander.common.exception.TokenException;
import com.nowander.common.pojo.po.User;
import com.nowander.common.utils.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT: https://blog.csdn.net/weixin_45070175/article/details/118559272
 * Header: alg 签名算法，默认为HMAC SHA256（写为HS256）; typ 令牌类型，JWT令牌统一写为JWT。
 * Payload:
 *      iss：发行人
 *      exp：到期时间
 *      sub：主题
 *      aud：用户
 *      nbf：在此之前不可用
 *      iat：发布时间
 * jti：JWT ID用于标识该JWT
 * @author wang tengkun
 * @date 2022/2/23
 */
@Component
@AllArgsConstructor
public class TokenService {

    private JwtConfig jwtConfig;
    private RedisTemplate<String, String> redisTemplate;

    @NonNull
    public String refleshToken(HttpServletRequest request) {
        String refleshToken = request.getHeader(jwtConfig.getRefleshHeader());
        this.requireVaildToken(refleshToken);
        JSONObject claims = TokenUtil.parse(refleshToken);
        User user = new User();
        user.setId(claims.get("id", Integer.class));
        user.setUsername(claims.get("username", String.class));
        Assert.notNull(user.getId());
        Assert.notBlank(user.getUsername());
        return this.createToken(user);
    }

    public String createToken(User user) {
        Map<String, Object> payload = new HashMap<>(8);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("avatar", user.getAvatar());
        return createToken(payload, false);
    }

    public String createRefleshToken(User user) {
        Map<String, Object> payload = new HashMap<>(8);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        return createToken(payload, true);
    }

    /**
     * 生成jwt
     * @param payload 数据主体
     * @param isReflesh
     * @return
     */
    public String createToken(Map<String, Object> payload, boolean isReflesh) {
        // 每个jwt都默认生成一个到期时间
        if (isReflesh) {
            payload.put(JWT.EXPIRES_AT, System.currentTimeMillis() + jwtConfig.getRefleshExpireMs());
        } else {
            payload.put(JWT.EXPIRES_AT, System.currentTimeMillis() + jwtConfig.getExpireMs());
        }
        payload.put(JWT.ISSUED_AT, System.currentTimeMillis());
        payload.put(JWT.ISSUER, jwtConfig.getIssuer());
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
    public boolean verifyToken(String token) {
        return JWTUtil.verify(token, jwtConfig.getKeyBytes());
    }

    /**
     * 获取token并进行校验
     * @param request
     * @return
     * @throws TokenException 当token缺失、过期、非法时抛出
     */
    @NonNull
    public String requireVaildToken(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getTokenHeader());
        requireVaildToken(token);
        assert token != null;
        return token;
    }

    /**
     * 检查token是否有效、过期
     * @param token
     */
    public void requireVaildToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new TokenException(ApiInfo.TOKEN_MISSING);
        }
        if (!verifyToken(token)) {
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
        if (TokenUtil.expiredToken(token)) {
            // 过期
            throw new TokenException(ApiInfo.TOKEN_EXP);
        }
        String username = (String) TokenUtil.parseAndGet(token, "username");
        if (redisTemplate.opsForValue().get(RedisKeyPrefix.USER_TOKEN_BLACKLIST + username) != null) {
            // 虽然token还没过期，但username在黑名单中，说明用户已登出，不能使用token，必须重新登录
            throw new TokenException(ApiInfo.TOKEN_INVALID);
        }
    }

    /**
     * 通过req获取JWT中包含的User信息
     * @param request
     * @return
     * @throws TokenException
     */
    public User requireUserByToken(HttpServletRequest request) throws TokenException {
        String vaildToken = requireVaildToken(request);
        JSONObject tokenClaims = TokenUtil.parse(vaildToken);
        return createUser(tokenClaims);
    }

    private User createUser(JSONObject tokenClaims) {
        User user = new User();
        user.setId(tokenClaims.get("id", Integer.class));
        user.setUsername(tokenClaims.get("username", String.class));
        user.setAvatar(tokenClaims.get("avatar", String.class));
        Assert.notNull(user.getId());
        Assert.notBlank(user.getUsername());
        Assert.notBlank(user.getAvatar());
        return user;
    }
}

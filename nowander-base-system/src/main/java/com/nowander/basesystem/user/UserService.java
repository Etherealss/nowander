package com.nowander.basesystem.user;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.infrastructure.enums.RedisKeyPrefix;
import com.nowander.basesystem.user.security.jwt.JwtConfig;
import com.nowander.basesystem.user.security.jwt.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, SysUser> {

    RedisTemplate<String, Object> redisTemplate;
    JwtConfig jwtConfig;


    public void logout(HttpServletRequest request) {
        JSONObject claims = TokenUtil.parse(request.getHeader(jwtConfig.getTokenHeader()));
        Long exp = claims.get(JWT.EXPIRES_AT, Long.class);
        String username = claims.get("username", String.class);
        Assert.notNull(exp);
        Assert.notBlank(username);
        // 加入黑名单，事token失效（严格来说，黑名单是username的黑名单，用户必须再次使用账号密码登录才能才黑名单移除）
        redisTemplate.opsForValue().set(RedisKeyPrefix.USER_TOKEN_BLACKLIST + username, "",
                exp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        // 清空
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

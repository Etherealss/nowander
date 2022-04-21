package com.nowander.common.security.login;

import com.nowander.common.enums.RedisKeyPrefix;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.security.jwt.JwtConfig;
import com.nowander.common.user.service.TokenService;
import com.nowander.common.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description 登录成功处理器
 * @date 2021-10-05
 */
@Slf4j
@Component
@AllArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private TokenService tokenService;

    private RedisTemplate<String, String> redisTemplate;

    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        User user = (User) authentication.getPrincipal();
        log.info("登录成功，user：{}", user);

        // 获取token
        String token = tokenService.createToken(user);

//        redisTemplate.opsForValue().set(RedisKey.USER_TOKEN_BLACKLIST + user.getUsername(), token, jwtConfig.getExpireMs(), TimeUnit.SECONDS);
        // 登录成功，从黑名单中移除
        redisTemplate.opsForValue().getAndDelete(RedisKeyPrefix.USER_TOKEN_BLACKLIST + user.getUsername());
        Msg<Map<String, Object>> msg = Msg.ok("登录成功");
        Map<String, Object> data = new HashMap<>(4);
        data.put("user", user);
        data.put("token", token);
        msg.setData(data);
        ResponseUtil.send(response, msg);
    }
}
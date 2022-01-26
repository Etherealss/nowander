package com.nowander.framework.security;

import com.wanderfour.nowander.pojo.po.User;
import com.wanderfour.nowander.pojo.vo.Msg;
import com.wanderfour.nowander.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 登录成功处理器
 * @date 2021-10-05
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        log.debug("登录成功");
        User user = (User) authentication.getPrincipal();
        Msg<User> msg = Msg.ok("登录成功");
        msg.setData(user);
        ResponseUtil.send(response, msg);
    }
}

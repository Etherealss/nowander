package com.nowander.starter.controller.basesystem;

import com.nowander.basesystem.user.TokenService;
import com.nowander.basesystem.user.UserService;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousPostMapping;
import com.nowander.basesystem.user.security.login.LoginSuccessHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class SignController {

    private UserService userService;

    private TokenService tokenService;

    /**
     * 具体业务见 {@link LoginSuccessHandler }
     */
    @AnonymousPostMapping("/login")
    public void login() {
        log.info("用户登录");
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    @AnonymousGetMapping("/reflesh")
    public String reflesh(HttpServletRequest request) {
        return tokenService.refleshToken(request);
    }
}

package com.nowander.common.security;

import com.nowander.common.enums.ApiInfo;
import com.nowander.common.pojo.vo.Msg;
import com.nowander.common.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @author wtk
 * @description
 * @date 2021-09-05
 */
@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_MAPPING_URL = "/users/login";
    private static final String LOGIN_PAGE_PATH = "http://localhost:8080/#/login";
    private static final String DEFAULT_SUCCESS_URL = "/test.html";


    /** token过期时间（秒） */
    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 10;
    /** token刷新时间 */
    private static final int REFRESH_TOKEN_TIME = 60 * 60 * 10;
    private static final String PASSWORD = "secret";

    private UserDetailsService userDetailsService;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private MyJwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 将AuthenticationManager注入容器
     * @return
     * @throws Exception
     */
    @Override
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * IllegalStateException:https://blog.csdn.net/yhlly_/article/details/107348728
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * anonymous           |   匿名可以访问，已登录的用户不能访问
     * denyAll             |   用户不能访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 基于token，所以不需要session
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin()
                // 登录页面
                .loginPage(LOGIN_PAGE_PATH)
                // 登录请求访问的Controller路径
                .loginProcessingUrl(LOGIN_MAPPING_URL)
                // 登录成功后的跳转路径
                .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                .permitAll()
                // 成功处理器
                .successHandler(successHandler)
                // 失败处理器
                .failureHandler(failureHandler)
//                .usernameParameter("username").passwordParameter("password").permitAll()

                .and()
                // 认证失败处理类
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    log.debug("认证失败：{}", authException.getMessage());
                    Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
                    msg.setMessage(msg.getMessage() + authException.getMessage());
                    ResponseUtil.send(response, msg);
                })

                // 设置哪些路径可以直接访问，不需要认证
                .and()
                .authorizeRequests()
                // 对于登录login 验证码captcha 允许匿名访问
                .antMatchers("/test/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        // 放行Oauth2的API
                        "/oauth/**",
                        "/index.html",
                        "/captcha",
                        LOGIN_MAPPING_URL,
                        "/users/register",
                        "/lib/**",
                        "/toastr/**",
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/config/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()

                // 关闭CSRF防护
                .and()
                .csrf().disable()

                // 添加验证码过滤器
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

        ;
    }

    /**
     * 开启矩阵变量
     * @return
     */
    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}
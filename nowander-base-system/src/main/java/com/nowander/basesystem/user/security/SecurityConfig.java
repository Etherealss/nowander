package com.nowander.basesystem.user.security;

import com.nowander.basesystem.captcha.CaptchaService;
import com.nowander.basesystem.user.security.anonymous.RequestMethodEnum;
import com.nowander.basesystem.user.security.anonymous.annotation.AnonymousUrlUtil;
import com.nowander.basesystem.user.security.jwt.MyJwtAuthenticationFilter;
import com.nowander.basesystem.user.security.login.LoginAuthenticationFilter;
import com.nowander.basesystem.user.security.login.LoginFailureHandler;
import com.nowander.basesystem.user.security.login.LoginSuccessHandler;
import com.nowander.basesystem.user.security.login.UsernamePasswordCaptchaAuthProvider;
import com.nowander.infrastructure.enums.ApiInfo;
import com.nowander.infrastructure.exception.service.TokenException;
import com.nowander.infrastructure.pojo.Msg;
import com.nowander.infrastructure.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author wtk
 * @description
 * @date 2021-09-05
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String LOGIN_MAPPING_URL = "/users/login";
    private static final String LOGIN_PAGE_PATH = "http://localhost:8080/#/login";
    private static final String DEFAULT_SUCCESS_URL = "/test.html";
    private static final String[] PERMIT_LIST = {
            LOGIN_MAPPING_URL,
            "/users/reflesh",
            "/users/register",
            "/likes/**",
            "/likeCount/**",
            "/test/**",
            "/**/public/**"
    };

    private final RequestMappingHandlerMapping handlerMapping;
    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler successHandler;
    private final LoginFailureHandler failureHandler;
    private final MyJwtAuthenticationFilter jwtAuthenticationFilter;
    private final RedisTemplate<String, String> redisTemplate;
    private final CaptchaService captchaService;

    @Bean
    public UsernamePasswordCaptchaAuthProvider usernamePasswordCaptchaAuthProvider() {
        return new UsernamePasswordCaptchaAuthProvider(
                userDetailsService,
                redisTemplate,
                getPasswordEncoder(),
                captchaService
        );
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(successHandler, failureHandler);
        loginAuthenticationFilter.setAuthenticationManager(providerManager());
        return loginAuthenticationFilter;
    }

    @Bean
    public ProviderManager providerManager() {
        UsernamePasswordCaptchaAuthProvider provider = usernamePasswordCaptchaAuthProvider();
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(
                provider
        ));
        return providerManager;
    }

    /**
     * ??????????????? DaoAuthenticationProvider???
     * ????????????????????? HideUserNotFoundExceptions ????????? false???
     * ???SpringSecurity????????? UsernameNotFoundException
     * @return
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    /**
     * ???AuthenticationManager????????????
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
        auth.authenticationProvider(daoAuthenticationProvider())
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * IllegalStateException:https://blog.csdn.net/yhlly_/article/details/107348728
     * anyRequest          |   ????????????????????????
     * access              |   SpringEl??????????????????true???????????????
     * fullyAuthenticated  |   ????????????????????????????????????remember-me??????????????????
     * hasAnyAuthority     |   ??????????????????????????????????????????????????????????????????????????????
     * hasAnyRole          |   ??????????????????????????????????????????????????????????????????????????????
     * hasAuthority        |   ???????????????????????????????????????????????????????????????
     * hasIpAddress        |   ??????????????????????????????IP?????????????????????IP?????????????????????????????????
     * hasRole             |   ???????????????????????????????????????????????????????????????
     * anonymous           |   ???????????????????????????????????????????????????
     * denyAll             |   ??????????????????
     * permitAll           |   ????????????????????????
     * rememberMe          |   ????????????remember-me?????????????????????
     * authenticated       |   ????????????????????????
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // ???????????????????????????URL
        Map<String, Set<String>> anonymousUrls = AnonymousUrlUtil.getAnonymousUrl(handlerMapping);
        // ??????token??????????????????session
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //  ?????????????????????????????????????????? ?????? ??????????????????????????????????????????
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_PATH)
                // ?????????????????????Controller??????
                .loginProcessingUrl(LOGIN_MAPPING_URL)
                // ??????????????????????????????
                .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                .permitAll()
                // ???????????????
                .successHandler(successHandler)
                // ???????????????
                .failureHandler(failureHandler)

                // ?????????????????????????????????????????? ?????????????????????  ??????????????????????????????????????????
                .and()
                .exceptionHandling()
                .accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(getAuthenticationEntryPoint())

                //  ?????????????????????????????????????????? ????????????  ??????????????????????????????????????????
                // ??????????????????????????????????????????????????????
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/*/api-docs",
                        "/avatar/**",
                        "/file/**",
                        "/druid/**"
                ).permitAll()
                // ??????OPTIONS??????
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // ???????????????????????????url???????????????????????????Token??????????????????????????? Request ??????
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll()
                // ??????????????????????????????
                .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
                // ???????????????????????????????????????????????????
                .anyRequest().authenticated()
//                .anyRequest().permitAll()

                // ??????CSRF??????
                .and()
                .csrf().disable()

                // ?????????????????????????????????????????????
//                .cors().disable()

                // ??????token?????????
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // ?????????????????????
                .addFilterAfter(loginAuthenticationFilter(), MyJwtAuthenticationFilter.class);

        ;
    }


    @Override
    public void configure(WebSecurity web) {
        // ????????????????????????Security?????????
        web.ignoring().antMatchers(HttpMethod.GET,
                "/index.html",
                "/lib/**",
                "/toastr/**",
                "/img/**",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/webSocket/**",
                "/config/**"
        );
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            Msg<Object> msg;
            Object e = request.getAttribute("tokenException");
            if (e == null) {
                log.debug("???????????????{}", authException.getMessage());
                msg = new Msg<>(ApiInfo.AUTHORIZATION_FAILED);
                msg.setMessage(msg.getMessage() + authException.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                TokenException tokenException = (TokenException) e;
                log.debug("???????????????{}", tokenException.getMessage());
                msg = new Msg<>();
                msg.setCode(tokenException.getCode());
                msg.setMessage(tokenException.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
            ResponseUtil.send(response, msg);
        };
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return (request, response, authException) -> {
            log.debug("???????????????{}", authException.getMessage());
            Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
            msg.setMessage(msg.getMessage() + authException.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            ResponseUtil.send(response, msg);
        };
    }

    /**
     * ??????????????????
     * @return
     */
    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}

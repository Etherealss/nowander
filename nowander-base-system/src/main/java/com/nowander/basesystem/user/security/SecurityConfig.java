package com.nowander.basesystem.user.security;

import com.nowander.basesystem.user.security.jwt.MyJwtAuthenticationFilter;
import com.nowander.basesystem.user.security.login.LoginAuthenticationFilter;
import com.nowander.basesystem.user.security.login.LoginFailureHandler;
import com.nowander.basesystem.user.security.login.LoginSuccessHandler;
import com.nowander.basesystem.user.security.login.UsernamePasswordCaptchaAuthProvider;
import com.nowander.common.enums.ApiInfo;
import com.nowander.common.exception.TokenException;
import com.nowander.common.pojo.Msg;
import com.nowander.common.utils.ResponseUtil;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
            "/",
            "/index.html",
            LOGIN_MAPPING_URL,
            "/users/reflesh",
            "/users/register",
            "/lib/**",
            "/toastr/**",
            "/css/**",
            "/img/**",
            "/js/**",
            "/config/**",
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

    @Bean
    public UsernamePasswordCaptchaAuthProvider usernamePasswordCaptchaAuthProvider() {
        return new UsernamePasswordCaptchaAuthProvider(
                userDetailsService,
                redisTemplate,
                getPasswordEncoder()
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
     * 自定义配置 DaoAuthenticationProvider，
     * 唯一的目的是将 HideUserNotFoundExceptions 设置为 false，
     * 使SpringSecurity不隐藏 UsernameNotFoundException
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

                //  —————————————— 登录 ——————————————
                .and()
                .formLogin()
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

                // —————————————— 认证失败处理类  ——————————————
                .and()
                .exceptionHandling()
                .accessDeniedHandler(getAccessDeniedHandler())
                .authenticationEntryPoint(getAuthenticationEntryPoint())

                //  —————————————— 权限校验  ——————————————
                // 设置哪些路径可以直接访问，不需要认证
                .and()
                .authorizeRequests()
                // 对于登录login 验证码captcha 允许匿名访问
                .antMatchers(HttpMethod.GET, PERMIT_LIST).anonymous()
                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
                .anyRequest().permitAll()

                // 关闭CSRF防护
                .and()
                .csrf().disable()

                // 跨域。不知道实际作用，先注释掉
//                .cors().disable()

                // 添加token过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加登录过滤器
                .addFilterAfter(loginAuthenticationFilter(), MyJwtAuthenticationFilter.class);

        ;
    }

    @Override
    public void configure(WebSecurity web) {
        // 跳过Security过滤链
        web.ignoring().antMatchers(HttpMethod.GET, PERMIT_LIST);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, method) -> {
            // 带IgnoreAuth注解的方法直接放行
            // 根据请求类型做不同的处理
            for (RequestMethod requestMethod : info.getMethodsCondition().getMethods()) {
                if (Objects.isNull(method.getMethodAnnotation(IgnoreAuth.class))) {
                    return;
                }
                switch (requestMethod) {
                    case GET:
                        // getPatternsCondition得到请求url数组，遍历处理
                        ignoreAuth(method, web, HttpMethod.GET);
                        break;
                    case POST:
                        ignoreAuth(method, web, HttpMethod.POST);
                        break;
                    case DELETE:
                        ignoreAuth(method, web, HttpMethod.DELETE);
                        break;
                    case PUT:
                        ignoreAuth(method, web, HttpMethod.PUT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void ignoreAuth(HandlerMethod method, WebSecurity web, HttpMethod httpMethod) {
//        // 获取方法上的注解
//        RequestMapping mapping = method.getMethodAnnotation(RequestMapping.class);
//        if (mapping == null) {
//            return;
//        }
//        // 获取类的注解
//        RequestMapping mappingClass = method.getBeanType().getAnnotation(RequestMapping.class);
//        String path = (mappingClass == null ? "" : mappingClass.value()[0]) + mapping.value()[0];
//        path = path.replaceAll("\\{\\w+\\}", "*");
//        path = path.trim();
//        if (StringUtils.isBlank(path)) {
//            return;
//        }
//        log.info("Spring Security 忽略的路径：{}", path);
//        web.ignoring().antMatchers(httpMethod, path);
    }
    private void ignoreAuth(RequestMappingInfo info, WebSecurity web, HttpMethod httpMethod) {
        PathPatternsRequestCondition condition = info.getPathPatternsCondition();
        if (condition == null) {
            return;
        }
        for (PathPattern pattern : condition.getPatterns()) {
            String ignroePath = pattern.getPatternString().replaceAll("\\{\\w+\\}", "*");
            log.info("Spring Security 忽略的路径：{}", ignroePath);
            web.ignoring().antMatchers(httpMethod, ignroePath);
        }
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            Msg<Object> msg;
            Object e = request.getAttribute("tokenException");
            if (e == null) {
                log.debug("认证失败：{}", authException.getMessage());
                msg = new Msg<>(ApiInfo.AUTHORIZATION_FAILED);
                msg.setMessage(msg.getMessage() + authException.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                TokenException tokenException = (TokenException) e;
                log.debug("认证失败：{}", tokenException.getMessage());
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
            log.debug("拒绝访问：{}", authException.getMessage());
            Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
            msg.setMessage(msg.getMessage() + authException.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            ResponseUtil.send(response, msg);
        };
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

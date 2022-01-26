package com.nowander.framework.security;

import com.wanderfour.nowander.common.enums.ApiInfo;
import com.wanderfour.nowander.pojo.vo.Msg;
import com.wanderfour.nowander.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务配置类
 * https://www.bilibili.com/video/BV1vK4y1H7b1?p=24
 * @author wtk
 * @date 2021-10-25
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * authorizeRequests() 表示即将开始访问控制
     * 后面使用 antMatchers 匹配访问路径，再通过permitAll()等方法进行放行操作
     * 允许使用 RequestMatcher 实现基于 HttpServletRequest 限制访问
     * <p>
     * anyRequest() 在之前认证过程中我们就已经使用过 anyRequest()，表示匹配所有的请求。一般情况下此方法都会
     * 使用，设置全部内容都需要进行认证。
     * <p>
     * permitAll()表示所匹配的 URL 任何人都允许访问。
     * authenticated()表示所匹配的 URL 都需要被认证才能访问。
     * <p>
     * anonymous()表示可以匿名访问匹配的URL。和permitAll()效果类似，只是设置为 anonymous()的 url 会执行 filter 链中
     * <p>
     * access() 登录用户权限判断实际上底层实现都是调用access(表达式)
     * 例如：{@code .antMatchers("users/**").access("permitAll");} 相当于 {@code .antMatchers("users/**").permitAll()}
     * <p>
     * requestMatchers API文档：https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#authorizeRequests()
     * authorizeRequests API文档：https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#requestMatchers()
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 放行授权的资源
        // https://blog.csdn.net/qq_33371766/article/details/107146220
//        http
//                .authorizeRequests().antMatchers("/public/**", "/captcha/**", "/users/login", "/slideshow/**").permitAll().and()
//                .authorizeRequests().anyRequest().authenticated()
//        ;
        http.authorizeRequests().anyRequest().permitAll();

        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.debug("认证失败，无法访问系统资源。原因：{}", authException.getMessage());
            Msg<Object> msg = new Msg<>(ApiInfo.FORBIDDEN_REQUEST);
            msg.setMessage(authException.getMessage());
            ResponseUtil.send(response, msg);
        });
    }
}

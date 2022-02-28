package com.nowander.common.web;

import com.nowander.common.web.CurrentUserArgumentResolver;
import com.nowander.common.web.JsonArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMVC自定义配置，添加一些自定义功能
 * @author wtk
 * @date 2022-01-26
 */
@Slf4j(topic = "other")
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private CurrentUserArgumentResolver currentUserArgumentResolver;

    @Bean
    public JsonArgumentResolver getJsonArgumentResolver(){
        return new JsonArgumentResolver();
    }

    /**
     * 将自定义参数解析器注册进SpringMVC
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserArgumentResolver);
        argumentResolvers.add(getJsonArgumentResolver());
    }
}

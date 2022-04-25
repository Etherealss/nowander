package com.nowander.infrastructure.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
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
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final List<HandlerMethodArgumentResolver> customerArgumentResolvers;
    private final List<ConverterFactory<?, ?>> converterFactories;

    /**
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(customerArgumentResolvers);
        log.info("{}", argumentResolvers);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (ConverterFactory<?, ?> converterFactory : converterFactories) {
            registry.addConverterFactory(converterFactory);
        }
    }
}

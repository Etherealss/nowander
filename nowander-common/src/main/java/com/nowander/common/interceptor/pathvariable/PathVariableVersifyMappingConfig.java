package com.nowander.common.interceptor.pathvariable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author wang tengkun
 * @date 2022/2/25
 */
@Configuration
public class PathVariableVersifyMappingConfig {
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    private static PathVariableVersifyBean create4UUID() {
        PathVariableVersifyBean bean = new PathVariableVersifyBean();
        // Service的findById方法
        bean.setMethodName("findById");
        // Service的findById方法的参数
        // 注意，泛型参数在擦除后会变为 Object，如果想反射调用带泛型参数的方法，泛型参数对应的参数类型就要为Object
        bean.setArgsType(new Class[]{UUID.class});
        // 用于将PathVariable的参数值转换为UUID类型
        bean.setMap(UUID::fromString);
        return bean;
    }


    @Bean
    public PathVariableVersifyBean appIdVersifyBean() {
        PathVariableVersifyBean app = create4UUID();
        // PathVariable变量的变量名
        app.setPathVariableName("app_id");
        // 执行方法的bean名称，用于通过Spring容器获取Bean
        app.setBeanName("appService");
        return app;
    }

    @Bean
    public PathVariableVersifyBean frontendIdVersifyBean() {
        PathVariableVersifyBean app = create4UUID();
        app.setPathVariableName("frontend_app_id");
        app.setBeanName("frontendAppService");
        return app;
    }

}



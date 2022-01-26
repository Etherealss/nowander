package com.nowander.common.porperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @description 资源路径配置
 * @date 2021-08-13
 */
@Configuration
@ConfigurationProperties(prefix = "myapp.resource.path")
@Getter
@Setter
public class ResourcePathProperties {
    private String avatar;
}

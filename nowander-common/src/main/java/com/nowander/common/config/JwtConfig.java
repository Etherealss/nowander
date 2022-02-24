package com.nowander.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@Configuration
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtConfig {
    /**
     * 前端 token 存放的请求头的位置
     */
    private String header;
    /**
     * 密钥
     */
    private String key;
    private String sub;
    /**
     * 失效时间间隔 单位：秒
     */
    private Long expireSeconds;
    /**
     * key的byte[]
     */
    private byte[] keyBytes;

    @PostConstruct
    public void postConstruct() {
        keyBytes = key.getBytes();
    }

    /**
     * token类型
     */
    private String tokenType = "Bearer";

    /**
     * token类型的字符串长度，用于截取字符串
     */
    private int tokenTypeLength = tokenType.length() + 1;

    public static final String REDIS_CACHE_PREFIX = "token_";
}

package com.nowander.basesystem.user.security.jwt;

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
@ConfigurationProperties("app.jwt")
@Getter
@Setter
public class JwtConfig {
    /**
     * 前端 token 存放的请求头的位置
     */
    private String tokenHeader = "Auth";
    /**
     * 前端 reflesh token 存放的请求头的位置
     */
    private String refleshHeader = "reflesh_token";
    /**
     * 密钥
     */
    private String key;
    /**
     * 主题
     */
    private String subject;
    /**
     * 发行人
     */
    private String issuer = "NoWander";
    /**
     * token失效时间间隔
     */
    private Long expireMs;
    /**
     * reflesh token 的失效时间
     */
    private Long refleshExpireMs;
    /**
     * key的byte[]
     */
    private byte[] keyBytes;

    @PostConstruct
    public void postConstruct() {
        keyBytes = key.getBytes();
    }

    public static final String REDIS_CACHE_PREFIX = "token_";
}

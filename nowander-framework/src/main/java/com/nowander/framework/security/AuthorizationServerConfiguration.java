package com.nowander.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置类
 * 配置参考：
 * https://blog.csdn.net/qq_33371766/article/details/107146220
 * https://blog.csdn.net/caoweifeng12/article/details/106664237/
 * @author wtk
 * @date 2021-10-25
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * token过期时间（秒）
     */
    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 10;
    /**
     * token刷新时间
     */
    private static final int REFRESH_TOKEN_TIME = 60 * 60 * 10;
    private static final String PASSWORD = "secret";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Qualifier("jwtTokenStore")
    @Autowired
    private TokenStore tokenStore;
    @Qualifier("jwtAccessTokenConverter")
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Qualifier("myTokenEnhancer")
    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // jwt解析链
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(tokenEnhancer);
        delegates.add(accessTokenConverter);
        chain.setTokenEnhancers(delegates);
        // 密码模式
        endpoints.userDetailsService(userDetailsService)
                // 授权管理器
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(chain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端ID
                .withClient("client_id")
                // 授权码模式
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
                // 授权用户的操作权限（授权范围）
                .scopes("all")
                // 客户端密码，需要加密。否则会报错：Encoded password does not look like BCrypt
                .secret(passwordEncoder.encode(PASSWORD))
                // token有效期为3600秒
                .accessTokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                // 刷新token有效期为7200秒
                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIME)
                // 重定向地址，用户获取授权码
                .redirectUris("http://www.baidu.com");
    }
}

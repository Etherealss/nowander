package com.nowander.chat.config;

import com.nowander.chat.core.socket.WebSocketConnectionHandler;
import com.nowander.chat.core.socket.WebSocketHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author wtk
 * @date 2022/2/18
 */
@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private WebSocketConnectionHandler webSocketConnectionHandler;

    private WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                //添加myHandler消息处理对象，和websocket访问地址
                .addHandler(webSocketConnectionHandler, "websocket")
                //设置允许跨域访问
                .setAllowedOrigins("*")
                //添加拦截器可实现用户链接前进行权限校验等操作
                .addInterceptors(webSocketHandshakeInterceptor);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        // 设置超时断开时间
        container.setMaxSessionIdleTimeout(1000 * 60 * 60L);
        return container;
    }
}
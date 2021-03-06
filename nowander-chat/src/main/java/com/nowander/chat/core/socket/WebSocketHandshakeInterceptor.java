package com.nowander.chat.core.socket;

import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.jwt.JwtConfig;
import com.nowander.basesystem.user.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * WebSocket连接
 * @author wtk
 */
@Component
@AllArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private JwtConfig jwtConfig;

    private TokenService tokenService;

    private ChatContext chatContext;

    /**
     * 握手之前校验token，校验不通过则抛异常
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = request.getHeaders().getFirst(jwtConfig.getTokenHeader());
        SysUser sysUser = tokenService.requireUserByToken(token);
        chatContext.setUser(sysUser);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
            response, WebSocketHandler wsHandler, Exception exception) {
    }

}

package com.nowander.chat.core.socket;

import com.nowander.basesystem.user.SysUser;
import com.nowander.chat.domain.event.connect.CloseConnectionEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * @author wtk
 */
@Component
@AllArgsConstructor
public class WebSocketConnectionHandler extends AbstractWebSocketHandler {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatContext chatContext;
    private final EventDispatcher eventDispatcher;

    /**
     * socket 建立成功事件
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        SysUser sysUser = chatContext.getUser();
        chatContext.putSession(sysUser.getId(), session);
    }

    /**
     * 接收消息事件
     * @param session 发送者
     * @param message 发送的数据
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        eventDispatcher.doDispatch(session, message);
    }

    /**
     * socket 连接断开事件
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        SysUser sysUser = chatContext.getUser();
        chatContext.removeUser();
        Integer userId = sysUser.getId();
        chatContext.removeSession(userId);
        applicationEventPublisher.publishEvent(new CloseConnectionEvent(session, null, sysUser));
    }

}

package com.nowander.chat.core.socket;

import com.nowander.chat.domain.event.connect.ConnectionClosedEvent;
import com.nowander.common.pojo.po.User;
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
public class WebSocketConnectionHandler extends AbstractWebSocketHandler {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatContextHolder chatContextHolder;

    public WebSocketConnectionHandler(ApplicationEventPublisher applicationEventPublisher, ChatContextHolder chatContextHolder) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.chatContextHolder = chatContextHolder;
    }

    /**
     * socket 建立成功事件
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        User user = chatContextHolder.getUser();
        chatContextHolder.putSession(user.getId(), session);
    }

    /**
     * 接收消息事件
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    /**
     * socket 连接断开事件
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        User user = chatContextHolder.getUser();
        chatContextHolder.removeUser();
        Integer userId = user.getId();
        chatContextHolder.removeSession(userId);
        applicationEventPublisher.publishEvent(new ConnectionClosedEvent(userId, user.getUsername()));
    }

}

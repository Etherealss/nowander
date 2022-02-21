package com.nowander.chat.socket;

import com.nowander.chat.event.ConnectionClosedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 */
@AllArgsConstructor
@Component
public class HttpAuthHandler extends AbstractWebSocketHandler {

    private final ApplicationEventPublisher applicationEventPublisher;
    private static ConcurrentHashMap<Integer, WebSocketSession> sessionPools = new ConcurrentHashMap<>();


    /**
     * socket 建立成功事件
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
    }

    /**
     * 检查是否可以建立连接
     * @param session
     * @param userId
     * @return
     * @throws IOException
     */
    private boolean checkCondition(WebSocketSession session, Integer userId) throws IOException {
        return true;
    }

    /**
     * 接收消息事件
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("获取到消息 >> " + message.getPayload());
        session.sendMessage(new TextMessage(String.format("收到用户：【%s】发来的【%s】",
                session.getAttributes().get("terminalId"), message.getPayload())));
    }

    /**
     * socket 连接断开事件
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String terminalId = session.getAttributes().get("terminalId").toString();
        String enterpriseCode = session.getAttributes().get("enterpriseCode").toString();
        sessionPools.remove(terminalId);
        applicationEventPublisher.publishEvent(new ConnectionClosedEvent(terminalId, enterpriseCode));
    }

}

package com.nowander.chat.core.socket;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wtk
 * @date 2022-03-05
 */
@Component
public class EventFactory {

    public ApplicationEvent getEvent(WebSocketSession session, TextMessage message) {
        return null;
    }
}

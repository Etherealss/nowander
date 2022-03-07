package com.nowander.chat.core.socket;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wtk
 * @date 2022-03-05
 */
@Component
public class EventDispatcher {

    private ApplicationEventPublisher applicationEventPublisher;
    private EventFactory eventFactory;

    public void doDispatch(WebSocketSession session, TextMessage message) {
        applicationEventPublisher.publishEvent(eventFactory.getEvent(session, message));
    }
}

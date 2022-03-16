package com.nowander.chat.core.socket;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.common.pojo.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-03-05
 */
@Component
public class EventDispatcher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Map<String, EventProvider> eventProviders;
    private final ChatContext chatContext;

    public EventDispatcher(ApplicationEventPublisher applicationEventPublisher, List<EventProvider> eventProviders, ChatContext chatContext) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventProviders = eventProviders.stream().collect(
                Collectors.toMap((EventProvider::support),
                        (eventProvider -> eventProvider))
        );
        this.chatContext = chatContext;
    }

    public ChatEvent getEvent(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        JSONObject json = JSONUtil.parseObj(payload);
        String eventType = json.getStr("event_type");
        return eventProviders.get(eventType).get(session, json, chatContext.getUser());
    }

    public void doDispatch(WebSocketSession session, TextMessage message) {
        applicationEventPublisher.publishEvent(this.getEvent(session, message));
    }
}

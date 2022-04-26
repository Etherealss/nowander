package com.nowander.chat.core.socket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.infrastructure.exception.rest.ErrorParamException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private final Validator GLOBAL_VALIDATOR;

    public EventDispatcher(ApplicationEventPublisher applicationEventPublisher, List<EventProvider> eventProviders, ChatContext chatContext) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventProviders = eventProviders.stream().collect(
                Collectors.toMap((EventProvider::supportType),
                        (eventProvider -> eventProvider))
        );
        this.chatContext = chatContext;
        GLOBAL_VALIDATOR = SpringUtil.getBean(Validator.class);
    }

    private ChatEvent getEvent(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        JSONObject json = JSONUtil.parseObj(payload);
        String eventType = json.getStr("eventType");
        return eventProviders.get(eventType).get(session, json, chatContext.getUser());
    }

    private boolean validate(ChatEvent event) {
        Set<ConstraintViolation<ChatEvent>> results = GLOBAL_VALIDATOR.validate(event);
        if (results.isEmpty()) {
            return true;
        } else {
            StringBuilder stringBuilder = new StringBuilder("event 参数检验不通过：");
            // 获取和拼接所有错误信息
            for (Iterator<ConstraintViolation<ChatEvent>> it = results.iterator(); it.hasNext();) {
                ConstraintViolation<ChatEvent> violation = it.next();
                stringBuilder.append(violation.getPropertyPath())
                        .append(":")
                        .append(violation.getMessage());
                if (it.hasNext()) {
                    stringBuilder.append("; ");
                }
            }
            stringBuilder.append("。");
            throw new ErrorParamException(stringBuilder.toString());
        }
    }

    public void doDispatch(WebSocketSession session, TextMessage message) {
        ChatEvent event = this.getEvent(session, message);
        validate(event);
        applicationEventPublisher.publishEvent(event);
    }
}

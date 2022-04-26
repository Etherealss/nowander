package com.nowander.infrastructure.pojo;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author wtk
 */
@Getter
public abstract class DomainEvent extends ApplicationEvent {
    private final String id;
    private final Integer userId;
    private final LocalDateTime occurredOn;

    public DomainEvent(Integer userId) {
        this(userId, userId);
    }

    public DomainEvent(Object o, Integer userId) {
        super(o);
        this.userId = userId;
        occurredOn = LocalDateTime.now();
        id = UUID.randomUUID().toString().replace("-", "");
    }
}

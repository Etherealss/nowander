package com.nowander.common.pojo;

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
    private final String username;
    private final LocalDateTime occurredOn;

    public DomainEvent(Integer userId, String username) {
        this(userId, userId, username);
    }

    public DomainEvent(Object o, Integer userId, String username) {
        super(o);
        this.userId = userId;
        this.username = username;
        occurredOn = LocalDateTime.now();
        id = UUID.randomUUID().toString().replace("-", "");
    }

    public boolean match(String type) {
        return supportType() != null && supportType().equals(type);
    }

    public String supportType() {
        return null;
    }
}

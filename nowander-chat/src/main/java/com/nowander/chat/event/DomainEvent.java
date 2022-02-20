package com.nowander.chat.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * </p>
 */
@Getter
public abstract class DomainEvent extends ApplicationEvent {
    private String id;
    private LocalDateTime occurredOn;

    public DomainEvent(Object o) {
        super(o);
        occurredOn = LocalDateTime.now();
        id = UUID.randomUUID().toString().replace("-", "");
    }
}

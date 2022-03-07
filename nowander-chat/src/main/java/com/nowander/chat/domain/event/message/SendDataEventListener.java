package com.nowander.chat.domain.event.message;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2022-03-05
 */
@Component
public class SendDataEventListener {

    @EventListener(SendTextEvent.class)
    public void watch(SendTextEvent sendTextEvent) {

    }
}

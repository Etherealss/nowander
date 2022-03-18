package com.nowander.chat.domain.listener;

import com.nowander.chat.domain.event.message.SendTextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wang tengkun
 * @date 2022/3/17
 */
@Component
public class SendEventListener {



    @EventListener(SendTextEvent.class)
    public void handleEvent(SendTextEvent event) {

    }


}

package com.nowander.chat.domain.event.message;

import com.nowander.common.pojo.DomainEvent;

/**
 * @author wtk
 * @date 2022-03-05
 */
public class SendTextEvent extends DomainEvent {
    public SendTextEvent(Integer userId, String username) {
        super(userId, userId, username);
    }

    @Override
    public String supportType() {
        return "send_text";
    }
}

package com.nowander.chat.domain.event;

import cn.hutool.json.JSONObject;
import com.nowander.common.pojo.DomainEvent;
import com.nowander.common.pojo.po.User;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wang tengkun
 * @date 2022/3/16
 */
@Getter
public abstract class ChatEvent extends DomainEvent {
    private final WebSocketSession session;
    private final JSONObject attrs;

    public ChatEvent(WebSocketSession session, JSONObject attrs, User user) {
        super(user.getId(), user.getUsername());
        this.session = session;
        this.attrs = attrs;
    }
}

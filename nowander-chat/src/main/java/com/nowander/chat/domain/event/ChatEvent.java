package com.nowander.chat.domain.event;

import cn.hutool.json.JSONObject;
import com.nowander.basesystem.user.SysUser;
import com.nowander.infrastructure.pojo.DomainEvent;
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

    public ChatEvent(WebSocketSession session, JSONObject attrs, SysUser sysUser) {
        super(sysUser.getId(), sysUser.getUsername());
        this.session = session;
        this.attrs = attrs;
    }
}

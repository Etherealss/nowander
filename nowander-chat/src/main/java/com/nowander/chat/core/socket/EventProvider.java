package com.nowander.chat.core.socket;

import cn.hutool.json.JSONObject;
import com.nowander.chat.domain.event.ChatEvent;
import com.nowander.basesystem.user.SysUser;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wang tengkun
 * @date 2022/3/16
 */
public interface EventProvider {
    String supportType();
    ChatEvent get(WebSocketSession session, JSONObject attrs, SysUser sysUser);
}

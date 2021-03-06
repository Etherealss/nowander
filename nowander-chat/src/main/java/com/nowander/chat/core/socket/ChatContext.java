package com.nowander.chat.core.socket;

import com.nowander.basesystem.user.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Component
public class ChatContext {
    private final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();
    private static final ConcurrentHashMap<Integer, WebSocketSession> SESSION_POOLS = new ConcurrentHashMap<>();

    public void setUser(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public SysUser getUser() {
        return userHolder.get();
    }

    public void removeUser() {
        userHolder.remove();
    }

    public void putSession(Integer key, WebSocketSession session) {
        SESSION_POOLS.put(key, session);
    }

    public WebSocketSession getSession(Integer key) {
        return SESSION_POOLS.get(key);
    }

    public WebSocketSession removeSession(Integer key) {
        return SESSION_POOLS.remove(key);
    }
}

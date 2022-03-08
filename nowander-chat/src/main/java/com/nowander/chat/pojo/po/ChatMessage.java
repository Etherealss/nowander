package com.nowander.chat.pojo.po;

import com.nowander.common.pojo.IdentifiedEntity;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
public class ChatMessage extends IdentifiedEntity {
    private Integer senderId;
    private Integer receverId;
    private String senderUsername;
    private String receverUsername;
    private String content;
}

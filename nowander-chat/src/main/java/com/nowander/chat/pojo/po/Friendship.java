package com.nowander.chat.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import com.nowander.infrastructure.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("friendship")
@NoArgsConstructor
public class Friendship extends BaseEntity {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户的好友的id
     */
    private Integer friendId;

    public Friendship(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}

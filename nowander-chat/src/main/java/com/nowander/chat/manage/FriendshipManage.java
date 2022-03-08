package com.nowander.chat.manage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.chat.mapper.FriendshipMapper;
import com.nowander.chat.pojo.po.Friendship;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
@AllArgsConstructor
public class FriendshipManage extends ServiceImpl<FriendshipMapper, Friendship> {
    FriendshipMapper friendshipMapper;
    public List<Integer> getAllFriendIds(Integer userId) {
        return friendshipMapper.getFriendIdList(userId);
    }

    public Friendship getFriendship(Integer userId, Integer friendId) {
        return friendshipMapper.getByUserIdAndFriendId(userId, friendId);
    }

    public void deleteByUserIdAndFriendId(Integer userId, Integer friendId) {
        friendshipMapper.deleteByUserIdAndFriendId(userId, friendId);
    }
}


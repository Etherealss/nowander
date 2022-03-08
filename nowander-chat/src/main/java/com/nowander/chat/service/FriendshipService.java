package com.nowander.chat.service;

import com.nowander.chat.manage.FriendshipManage;
import com.nowander.chat.pojo.po.Friendship;
import com.nowander.common.pojo.po.User;
import com.nowander.common.user.manage.UserManage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @since 2022-03-08
 */
@Service
@Slf4j
@AllArgsConstructor
public class FriendshipService {
    private UserManage userManage;
    private FriendshipManage friendshipManage;

    public List<User> getFriends(User user) {
        List<Integer> friendIds = friendshipManage.getAllFriendIds(user.getId());
        return friendIds.stream().map(userManage::getById).collect(Collectors.toList());
    }

    public boolean isFriend(User user, Integer friendId) {
        return friendshipManage.getFriendship(user.getId(), friendId) != null;
    }

    public void save(User user, Integer friendId) {
        friendshipManage.save(new Friendship(user.getId(), friendId));
    }

    public void delete(User user, Integer friendId) {
        friendshipManage.deleteByUserIdAndFriendId(user.getId(), friendId);
    }


}

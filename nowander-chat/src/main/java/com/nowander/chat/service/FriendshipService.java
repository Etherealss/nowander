package com.nowander.chat.service;

import com.nowander.basesystem.user.SysUser;
import com.nowander.chat.manage.FriendshipManage;
import com.nowander.chat.pojo.po.Friendship;
import com.nowander.basesystem.user.UserManage;
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

    public List<SysUser> getFriends(SysUser sysUser) {
        List<Integer> friendIds = friendshipManage.getAllFriendIds(sysUser.getId());
        return friendIds.stream().map(userManage::getById).collect(Collectors.toList());
    }

    public boolean isFriend(SysUser sysUser, Integer friendId) {
        return friendshipManage.getFriendship(sysUser.getId(), friendId) != null;
    }

    public void save(SysUser sysUser, Integer friendId) {
        friendshipManage.save(new Friendship(sysUser.getId(), friendId));
    }

    public void delete(SysUser sysUser, Integer friendId) {
        friendshipManage.deleteByUserIdAndFriendId(sysUser.getId(), friendId);
    }

    public void requestAddFriend(SysUser sysUser, Integer targetId, String message) {

    }


}

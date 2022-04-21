package com.nowander.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.chat.pojo.po.Friendship;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Mapper
@Repository
public interface FriendshipMapper extends BaseMapper<Friendship> {

    List<Integer> getFriendIdList(@Param("userId") Integer userId);
    Friendship getByUserIdAndFriendId(@Param("userId") Integer userId, @Param("friendId") Integer friendId);
    void deleteByUserIdAndFriendId(@Param("userId") Integer userId, @Param("friendId") Integer friendId);
}

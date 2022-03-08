package com.nowander.comment.strategy;

import cn.hutool.extra.spring.SpringUtil;
import com.nowander.comment.mapper.CommentMapper;
import com.nowander.common.pojo.po.User;
import com.nowander.common.user.mapper.UserMapper;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wtk
 * @date 2022-02-05
 */
@Data
public abstract class QueryCommentAndReplyStrategy {
    protected CommentMapper commentMapper = SpringUtil.getBean(CommentMapper.class);
    protected UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
    /**
     * 每页显示的评论数量
     */
    protected int commentSize;
    /**
     * 每页显示的评论回复数量
     */
    protected int replySize;
    /**
     * 当前页
     */
    protected int curPage;
    /**
     * 当前用户，用户判断是否为评论作者
     */
    protected User curUser;
    /**
     * 用于表示是否要附带某条回复的引用对象
     */
    protected boolean getRefer;

    /**
     * 评论中的用户可能会重复，为了避免重复查询以及传输数据，先记录用户的id，再统一查询并保存
     */
    private Set<Integer> userIdSet = new HashSet<>();

    public QueryCommentAndReplyStrategy(int commentSize, int replySize, int curPage, User curUser, boolean getRefer) {
        this.commentSize = commentSize;
        this.replySize = replySize;
        this.curPage = curPage;
        this.curUser = curUser;
        this.getRefer = getRefer;
    }

    /**
     * 添加评论作者的id，后续会通过这些id获取User记录，保存到Map中
     * @param userId
     */
    public void addAuthorId2Set(Integer userId) {
        userIdSet.add(userId);
    }

    /**
     * 获取评论作者信息
     * @return
     */
    public Map<Integer, User> getAuthorsData() {
        // TODO 获取部分信息
        List<User> users = userMapper.selectBatchIds(userIdSet);
        return users.stream()
                .collect(Collectors.toMap((User::getId), (user -> user)));
    }
}

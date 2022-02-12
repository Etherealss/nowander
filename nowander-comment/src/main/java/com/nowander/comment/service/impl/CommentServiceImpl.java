package com.nowander.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.comment.mapper.CommentMapper;
import com.nowander.comment.pojo.dto.CommentDto;
import com.nowander.comment.pojo.po.Comment;
import com.nowander.comment.service.CommentService;
import com.nowander.comment.strategy.CommentReplyContext;
import com.nowander.comment.strategy.comment.QueryCommentByLike;
import com.nowander.comment.strategy.comment.QueryCommentByTime;
import com.nowander.comment.strategy.comment.QueryCommentStrategy;
import com.nowander.comment.strategy.reply.QueryReplyByLike;
import com.nowander.comment.strategy.reply.QueryReplyByTime;
import com.nowander.comment.strategy.reply.QueryReplyStrategy;
import com.nowander.common.exception.NotAuthorException;
import com.nowander.common.exception.NotFoundException;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 服务实现类
 * @author wtk
 * @since 2022-01-05
 */
@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private CommentMapper commentMapper;

    @Override
    public Msg<IPage<CommentDto>> getHotComments(Long parentId, Long userId) {
        return null;
    }

    @Override
    public Msg<Map<String, Object>> pageComments(
            Integer parentId, Integer parentType, int curPage,
            int pageSize, int replySize, String orderBy, User user) {
        QueryCommentStrategy strategy;
        switch (orderBy) {
            case "time":
                strategy = new QueryCommentByTime(pageSize, replySize, curPage, user);
                break;
            case "like":
                strategy = new QueryCommentByLike(pageSize, replySize, curPage, user);
                break;
            default:
                throw new UnsupportedOperationException("不支持的排序方式");
        }
        Map<String, Object> map = CommentReplyContext.build4Comment(strategy)
                .query(parentId, parentType);
        return Msg.ok(map);
    }

    @Override
    public Msg<Map<String, Object>> pageReplys(
            Integer commentId, int curPage, int replySize, String orderBy, User user) {
        QueryReplyStrategy strategy;
        switch (orderBy) {
            case "like":
                strategy = new QueryReplyByLike(replySize, curPage, user);
                break;
            case "time":
                strategy = new QueryReplyByTime(replySize, curPage, user);
                break;
            default:
                throw new UnsupportedOperationException("不支持的排序方式");

        }
        Map<String, Object> map = CommentReplyContext.build4Reply(strategy)
                .query(commentId);
        return Msg.ok(map);
    }

    @Override
    public Msg<Object> deleteComment(Integer commentId, Integer authorId) {
        int i = commentMapper.deleteByAuthor(commentId, authorId);
        if (i == 0) {
            // 没有删除
            Integer count = commentMapper.selectCount(
                    new QueryWrapper<Comment>().eq("id", commentId));
            if (count == 0) {
                throw new NotFoundException("没有id为" + commentId + "的评论，删除失败");
            } else {
                throw new NotAuthorException("id为" + authorId + "的用户不是id为" + commentId + "的作者，无法删除");
            }
        }
        return Msg.ok();
    }
}

package com.nowander.forum.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.forum.comment.reply.strategy.QueryReplyByLike;
import com.nowander.forum.comment.reply.strategy.QueryReplyByTime;
import com.nowander.forum.comment.reply.strategy.QueryReplyStrategy;
import com.nowander.forum.comment.strategy.QueryCommentByLike;
import com.nowander.forum.comment.strategy.QueryCommentByTime;
import com.nowander.forum.comment.strategy.QueryCommentStrategy;
import com.nowander.infrastructure.exception.service.NotAuthorException;
import com.nowander.infrastructure.exception.service.NotFoundException;
import com.nowander.basesystem.user.SysUser;
import com.nowander.infrastructure.pojo.Msg;
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
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    private CommentMapper commentMapper;

    public Msg<IPage<CommentDto>> getHotComments(Long parentId, Long userId) {
        return null;
    }

    public Map<String, Object> pageComments(
            Integer parentId, Integer parentType, int curPage,
            int pageSize, int replySize, String orderBy, SysUser sysUser) {
        QueryCommentStrategy strategy;
        switch (orderBy) {
            case "time":
                strategy = new QueryCommentByTime(pageSize, replySize, curPage, sysUser);
                break;
            case "like":
                strategy = new QueryCommentByLike(pageSize, replySize, curPage, sysUser);
                break;
            default:
                throw new UnsupportedOperationException("不支持的排序方式");
        }
        Map<String, Object> map = CommentReplyContext.build4Comment(strategy)
                .query(parentId, parentType);
        return map;
    }

    public Map<String, Object> pageReplys(
            Integer commentId, int curPage, int replySize, String orderBy, SysUser sysUser) {
        QueryReplyStrategy strategy;
        switch (orderBy) {
            case "like":
                strategy = new QueryReplyByLike(replySize, curPage, sysUser);
                break;
            case "time":
                strategy = new QueryReplyByTime(replySize, curPage, sysUser);
                break;
            default:
                throw new UnsupportedOperationException("不支持的排序方式");

        }
        Map<String, Object> map = CommentReplyContext.build4Reply(strategy)
                .query(commentId);
        return map;
    }

    public void deleteComment(Integer commentId, Integer authorId) {
        int i = commentMapper.deleteByIdAndAuthor(commentId, authorId);
        if (i == 0) {
            // 没有删除
            Integer count = commentMapper.selectCount(
                    new QueryWrapper<Comment>().eq("id", commentId));
            if (count == 0) {
                throw new NotFoundException(Comment.class, commentId.toString());
            } else {
                throw new NotAuthorException("id为" + authorId + "的用户不是id为" + commentId + "的作者，无法删除");
            }
        }
    }
}
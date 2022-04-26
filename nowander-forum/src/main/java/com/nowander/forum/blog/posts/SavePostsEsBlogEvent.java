package com.nowander.forum.blog.posts;

import com.nowander.infrastructure.pojo.DomainEvent;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Getter
public class SavePostsEsBlogEvent extends DomainEvent {
    private final PostsEntity postsEntity;

    public SavePostsEsBlogEvent(Integer userId, PostsEntity postsEntity) {
        super(userId);
        this.postsEntity = postsEntity;
    }
}

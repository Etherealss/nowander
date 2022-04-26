package com.nowander.starter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.forum.blog.posts.PostsEntity;
import com.nowander.forum.blog.posts.PostsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {
    public static final int POSTS_PAGE_SIZE = 10;

    private final PostsService postsService;

    @AnonymousGetMapping("/{postsId}")
    public PostsEntity getById(@PathVariable Integer postsId) {
        return postsService.getById(postsId);
    }

    /**
     * 分页获取
     * @param curPage
     * @param orderBy 排序方式
     * @return
     */
    @AnonymousGetMapping("/pages/{curPage}")
    public IPage<PostsEntity> getPageCompetition(
            @PathVariable(value = "curPage") int curPage,
            @MatrixVariable(value = "orderBy", pathVar = "curPage", defaultValue = "time") String orderBy) {
        return postsService.page(curPage, POSTS_PAGE_SIZE, orderBy);
    }
}


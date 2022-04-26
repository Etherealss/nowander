package com.nowander.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.forum.blog.article.ArticleDetailCommand;
import com.nowander.forum.blog.article.ArticleDetailDTO;
import com.nowander.forum.blog.article.ArticleService;
import com.nowander.infrastructure.enums.OrderType;
import com.nowander.infrastructure.web.ResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
@ResponseAdvice
public class ArticleController {

    private ArticleService articleService;

    public static final int ARTICLE_PAGE_SIZE = 10;

    @AnonymousGetMapping("/details/{articleId}")
    public ArticleDetailDTO getArticleDetail(@PathVariable Integer articleId) {
        return articleService.getDetailById(articleId);
    }

    @PostMapping("/publish")
    public Integer publishArticle(@RequestBody @Validated ArticleDetailCommand article, SysUser user) {
        return articleService.save(article, user);
    }

    @PutMapping("/{articleId}")
    public void updateArticle(@PathVariable Integer articleId,
                              @RequestBody @Validated ArticleDetailCommand article,
                              SysUser user) {
        articleService.update(articleId, article, user);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable Integer articleId) {
        articleService.deleteById(articleId);
    }

    /**
     * 分页获取
     * @param curPage
     * @param orderBy 排序方式
     * @return
     */
    @AnonymousGetMapping("/pages/{curPage}")
    public IPage<ArticleDetailDTO> getPageCompetition(
            @PathVariable(value = "curPage") int curPage,
            @RequestParam(value = "orderBy", defaultValue = "time") OrderType orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        return articleService.pageDetails(curPage, ARTICLE_PAGE_SIZE, orderBy);
    }
}


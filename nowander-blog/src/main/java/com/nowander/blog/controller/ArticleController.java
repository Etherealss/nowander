package com.nowander.blog.controller;


import com.nowander.blog.service.ArticleService;
import com.nowander.common.pojo.po.Article;
import com.nowander.common.pojo.po.User;
import com.nowander.common.pojo.vo.Msg;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Slf4j
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/{articleId}")
    public Msg<Article> getArticle(@PathVariable Long articleId) {
        log.trace("获取文章");
        Article article = articleService.getById(articleId);
        return Msg.ok(article);
    }

    @PostMapping("/publish")
    public Msg<Object> publishArticle(Article article) {
        articleService.save(article);
        return Msg.ok();
    }

    @PutMapping("/update")
    public Msg<Object> updateArticle(Article article) {
        articleService.updateById(article);
        return Msg.ok();
    }

    @DeleteMapping("/{articleId}")
    public Msg<Object> deleteArticle(@PathVariable Long articleId) {
        articleService.removeById(articleId);
        return Msg.ok();
    }
}


package com.nowander.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.basesystem.user.SysUser;
import com.nowander.basesystem.user.security.anonymous.annotation.rest.AnonymousGetMapping;
import com.nowander.forum.blog.article.ArticleDetailCommand;
import com.nowander.forum.blog.article.ArticleDetailDTO;
import com.nowander.forum.blog.article.ArticleService;
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

    /* 矩阵变量：
       1.  使用;连接矩阵变量。请求路径写成：/cars/sell;low=34;brand=byd,audi,yd
       如果你的一个属性有多个值，有两种写法：
           1.  /cars/sell;low=34;brand=byd,audi,yd
           2.  /cars/sell;low=34;brand=byd;brand=audi;brand=yd
           第一个分号;之后的数据都是矩阵变量的参数
       2.  可以在多个地方写矩阵变量，在矩阵变量之后照常使用/即可：
           /cars/sell;low=34/1;brand=byd,audi,yd
       3.  可以声明是哪一个路径变量PathVariable后的矩阵变量：
           对于url：/boss/{bossId}/{empId}，访问的url为：/boss/1;age=20/2;age=18
           可以给@MatrixVariable指定pathVar参数：
           @MatrixVariable(value = "age", pathVal = "bossId")
    */

    /**
     * 分页获取
     * @param curPage
     * @param orderBy 排序方式
     * @return
     */
    @AnonymousGetMapping("/pages/{curPage}")
    public IPage<ArticleDetailDTO> getPageCompetition(
            @PathVariable(value = "curPage") int curPage,
            @MatrixVariable(value = "orderBy", pathVar = "curPage", defaultValue = "time") String orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        return articleService.pageDetails(curPage, ARTICLE_PAGE_SIZE, orderBy);
    }
}


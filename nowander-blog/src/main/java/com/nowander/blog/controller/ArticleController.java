package com.nowander.blog.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nowander.blog.pojo.po.Article;
import com.nowander.blog.pojo.vo.ArticleDetailVO;
import com.nowander.blog.service.ArticleService;
import com.nowander.common.annotation.ResponseAdvice;
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
@ResponseAdvice
public class ArticleController {

    private ArticleService articleService;

    public static final int ARTICLE_PAGE_SIZE = 10;

    @GetMapping("/public/{articleId}")
    public Article getArticle(@PathVariable Integer articleId) {
        log.trace("获取文章");
        return articleService.getById(articleId);
    }

    @PostMapping("/publish")
    public void publishArticle(ArticleDetailVO article) {
        articleService.save(article);
    }

    @PutMapping("/update")
    public void updateArticle(ArticleDetailVO article) {
        articleService.update(article);
    }

    @DeleteMapping("/public/{articleId}")
    public void deleteArticle(@PathVariable Long articleId) {
        articleService.removeById(articleId);
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
     *
     * @param curPage
     * @param orderBy
     * @return
     */
    @GetMapping("/public/pages/{curPage}")
    public IPage<Article> getPageCompetition(
            @PathVariable(value = "curPage") int curPage,
            @MatrixVariable(value = "orderBy", pathVar = "curPage", defaultValue = "time") String orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        return articleService.page(curPage, ARTICLE_PAGE_SIZE, orderBy);
    }
}


package com.nowander.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.pojo.po.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@DisplayName("ArticleMapperTest测试")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.nowander.starter.NowanderApplication.class)
@ComponentScan(basePackages = "com.nowander.blog.mapper")
class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testPageByLike() {
        IPage<Article> page = articleMapper.pageByLike(new Page<Article>(1, 3));
        log.debug("页数：{}", page.getPages());
        log.debug("总数：{}", page.getTotal());
        log.debug("当前：{}", page.getCurrent());
        log.debug("数量：{}", page.getSize());

        page.getRecords().forEach((a) -> {
            log.debug("{}", a);
        });
    }


    @Test
    void testInsert() {
        Article article = new Article();
        article.setAuthorId(1);
        article.setCategory(1);
        Set<String> labels = new HashSet<>(Arrays.asList("test1", "test2", "test3"));
        article.setLabels(labels);
        article.setTitle("test article");
        articleMapper.insert(article);
    }

    @Test
    void get() {
        Article article = articleMapper.selectById(28);
        log.debug("{}",article);
        log.debug("{}", Arrays.toString(article.getLabels().toArray()));
    }
}
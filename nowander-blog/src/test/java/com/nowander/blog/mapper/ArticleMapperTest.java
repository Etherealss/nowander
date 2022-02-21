package com.nowander.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.pojo.po.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@DisplayName("ArticleMapperTest测试")
@SpringBootTest
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
}
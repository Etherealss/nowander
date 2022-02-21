package com.nowander.blog.dao;

import com.nowander.blog.mapper.ArticleEsDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("ArticleEsDaoTest测试")
@SpringBootTest
class ArticleEsDaoTest {

    ArticleEsDao articleEsDao;

    @Test
    void testGet() {
    }
}
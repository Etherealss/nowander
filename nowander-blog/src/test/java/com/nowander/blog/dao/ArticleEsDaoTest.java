package com.nowander.blog.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("ArticleEsDaoTest测试")
@SpringBootTest
class ArticleEsDaoTest {

    ArticleEsDao articleEsDao;

    @Test
    void testGet() {
    }
}
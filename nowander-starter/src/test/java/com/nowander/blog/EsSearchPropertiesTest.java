package com.nowander.blog;

import com.nowander.blog.properties.EsSearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
@DisplayName("EsSearchPropertiesTest测试")
@SpringBootTest
class EsSearchPropertiesTest {
    @Autowired
    EsSearchProperties properties;
    @Test
    void test() {
        log.debug("{}", Arrays.toString(properties.getArticlePreTags()));
        log.debug("{}", Arrays.toString(properties.getArticlePostTags()));
        properties.getArticleFileds().forEach((k, v) -> log.debug("field:{}\tboost:{}", k, v));
    }
}
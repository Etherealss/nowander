package com.nowander.blog;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;

/**
 * @author wtk
 * @date 2022-02-10
 */
@Slf4j
@Getter
@Setter
@Configuration
@PropertySource("classpath:elasticsearch.yaml")
public class EsSearchProperties {
    @Value("${search.article.test2}")
    public String test2;
    @Value("${highlight.article.preTags}")
    public String[] articlePreTags;
    @Value("${highlight.article.postTags}")
    public String[] articlePostTags;
    @Value("${search.article.fields}")
    public Map<String, Float> articleFileds;

    @PostConstruct
    public void fun() {
        log.debug("{}", Arrays.toString(this.getArticlePreTags()));
        log.debug("{}", Arrays.toString(this.getArticlePostTags()));
        this.getArticleFileds().forEach((k, v) -> log.debug("field:{}\tboost:{}", k, v));
    }
}

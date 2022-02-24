package com.nowander.blog.properties;

import com.nowander.common.config.CompositePropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @author wtk
 * @date 2022-02-10
 */
@Slf4j
@Getter
@Setter
@Configuration
@PropertySource(value = "classpath:config/elasticsearch.yaml", factory = CompositePropertySourceFactory.class)
public class EsSearchProperties {

    @Value("${elasticsearch.highlight.article.pre-tags}")
    public String[] articlePreTags;
    @Value("${elasticsearch.highlight.article.post-tags}")
    public String[] articlePostTags;
    @Value("#{${elasticsearch.search.fields.article}}")
    public Map<String, Float> articleFileds;

}

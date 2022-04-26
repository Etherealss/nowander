package com.nowander.forum.blog.article;

import com.nowander.forum.blog.EsSearchProperties;
import com.nowander.forum.blog.NoWanderBlogEsEntity;
import com.nowander.forum.blog.NoWanderBlogEsService;
import com.nowander.forum.blog.article.content.ArticleContentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
public class ArticleEsService extends NoWanderBlogEsService {

    public ArticleEsService(EsSearchProperties properties, ElasticsearchRestTemplate es) {
        super(properties, es);
    }

    public NoWanderBlogEsEntity save(ArticleEntity article, ArticleContentEntity articleContent) {
        NoWanderBlogEsEntity esEntity = NoWanderBlogEsEntity.build(article, articleContent);
        return super.save(esEntity);
    }
}

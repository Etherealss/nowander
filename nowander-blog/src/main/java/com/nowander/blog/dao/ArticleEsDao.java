package com.nowander.blog.dao;

import com.nowander.common.pojo.po.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @date 2022-02-07
 */
@Repository
public interface ArticleEsDao extends ElasticsearchRepository<Article, Integer> {
}

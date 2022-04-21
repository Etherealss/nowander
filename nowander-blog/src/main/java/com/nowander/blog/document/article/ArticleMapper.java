package com.nowander.blog.document.article;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.document.NoWanderDocumentMapper;
import com.nowander.blog.document.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
interface ArticleMapper extends NoWanderDocumentMapper<Article> {
    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    @Override
    IPage<Article> pageByLike(@Param("page") Page<?> page);
}

package com.nowander.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.pojo.po.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Repository
public interface ArticleMapper extends NoWanderDocumentMapper<Article> {
    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    @Override
    IPage<Article> pageByLike(@Param("page") Page<?> page);
}

package com.nowander.forum.blog.article;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.blog.NoWanderBlogMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
interface ArticleMapper extends NoWanderBlogMapper<ArticleEntity> {
    /**
     * 按点赞数查询分页
     * @param page
     * @return
     */
    @Override
    IPage<ArticleEntity> pageByLike(@Param("page") Page<?> page);
}

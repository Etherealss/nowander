package com.nowander.blog.document.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowander.blog.document.article.ArticleContent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wtk
 * @since 2022-01-05
 */
@Mapper
@Repository
interface ArticleContentMapper extends BaseMapper<ArticleContent> {

}

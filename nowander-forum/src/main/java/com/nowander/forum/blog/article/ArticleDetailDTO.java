package com.nowander.forum.blog.article;

import com.nowander.forum.blog.article.content.ArticleContentEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

/**
 * @author wang tengkun
 * @date 2022/3/3
 */
@Data
public class ArticleDetailDTO {

    private Integer id;
    /**
     * 分区
     */
    private Integer category;

    /**
     * 作者Id
     */
    private Integer authorId;

    /**
     * 标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;

    /**
     * 标签
     */
    private Set<String> labels;

    /**
     * 收藏数
     */
    private Integer collected;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public static ArticleDetailDTO build(ArticleEntity articleEntity) {
        ArticleDetailDTO dto = new ArticleDetailDTO();
        BeanUtils.copyProperties(articleEntity, dto);
        return dto;
    }
    public static ArticleDetailDTO build(ArticleEntity articleEntity, ArticleContentEntity articleContentEntity) {
        ArticleDetailDTO dto = new ArticleDetailDTO();
        BeanUtils.copyProperties(articleEntity, dto);
        if (articleContentEntity != null) {
            // TODO 文章内容不能为null
            BeanUtils.copyProperties(articleContentEntity, dto);
        }
        return dto;
    }
}

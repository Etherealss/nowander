package com.nowander.forum.blog.article;

import com.nowander.forum.blog.article.content.ArticleContentEntity;
import com.nowander.infrastructure.pojo.DomainEvent;
import lombok.Getter;

/**
 * @author wang tengkun
 * @date 2022/4/26
 */
@Getter
public class SaveArticleEsBlogEvent extends DomainEvent {
    private final ArticleEntity article;
    private final ArticleContentEntity articleContent;
    public SaveArticleEsBlogEvent(Integer userId, ArticleEntity article, ArticleContentEntity articleContent) {
        super(userId);
        this.article = article;
        this.articleContent = articleContent;
    }
}

package com.nowander.forum.blog.article.content;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowander.forum.blog.article.ArticleDetailCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author wang tengkun
 * @date 2022/3/8
 */
@Service
@Slf4j
@AllArgsConstructor
public class ArticleContentService extends ServiceImpl<ArticleContentMapper, ArticleContent> {
    private ArticleContentMapper articleContentMapper;

    @Override
    public ArticleContent getById(Serializable id) {
        ArticleContent articleContent = articleContentMapper.selectById(id);
        return articleContent;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Integer articleId, ArticleDetailCommand command) {
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(articleId);
        articleContent.setContent(command.getContent());
        articleContentMapper.insert(articleContent);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateContent(Integer articleId, ArticleDetailCommand command) {
        ArticleContent entity = new ArticleContent();
        entity.setArticleId(articleId);
        entity.setContent(command.getContent());
        articleContentMapper.updateById(entity);
    }
}

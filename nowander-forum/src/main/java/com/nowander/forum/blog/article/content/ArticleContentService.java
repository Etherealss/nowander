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
public class ArticleContentService extends ServiceImpl<ArticleContentMapper, ArticleContentEntity> {
    private ArticleContentMapper articleContentMapper;

    @Override
    public ArticleContentEntity getById(Serializable id) {
        ArticleContentEntity articleContentEntity = articleContentMapper.selectById(id);
        return articleContentEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public ArticleContentEntity save(Integer articleId, ArticleDetailCommand command) {
        ArticleContentEntity articleContentEntity = new ArticleContentEntity();
        articleContentEntity.setId(articleId);
        articleContentEntity.setContent(command.getContent());
        articleContentMapper.insert(articleContentEntity);
        return articleContentEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateContent(Integer articleId, ArticleDetailCommand command) {
        ArticleContentEntity entity = new ArticleContentEntity();
        entity.setId(articleId);
        entity.setContent(command.getContent());
        ArticleContentEntity articleContentEntity = articleContentMapper.selectById(articleId);
        articleContentMapper.updateById(entity);
    }
}

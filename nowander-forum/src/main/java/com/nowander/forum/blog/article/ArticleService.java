package com.nowander.forum.blog.article;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.forum.blog.ArticleEsManage;
import com.nowander.forum.blog.DocEsDTO;
import com.nowander.forum.blog.article.content.ArticleContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author wtk
 * @date 2022-01-05
 */
@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

    private ArticleManage articleManage;
    private ArticleMapper articleMapper;
    private ArticleEsManage articleEsManage;

    public ArticleContent findContentById(Integer id) {
        return articleManage.findContentById(id);
    }

    public ArticleDetailDTO findDetailById(Integer id) {
        return articleManage.findDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer save(ArticleDetailCommand entity) {
        Integer id = articleManage.save(entity);
        // articleEsManage.save(buildDocEsDTO(entity));
        return id;
    }

    public boolean removeById(Serializable id) {
        return articleManage.removeById(id);
    }

    public void update(Integer articleId, ArticleDetailCommand command) {
        if (!StrUtil.isBlank(command.getContent())) {
            articleManage.updateContent(articleId, command);
        }
        articleManage.updateById(command);
    }

    public void deleteById(Integer id) {
        articleManage.removeById(id);
    }

    public IPage<Article> page(int curPage, int size, String orderBy) {
        IPage<Article> page;
        Page<Article> p = new Page<>(curPage, size);
        switch (orderBy) {
            case "time":
                page = articleMapper.selectPage(p,
                        new QueryWrapper<Article>().orderByDesc("create_time"));
                break;
            case "like":
                page = articleMapper.pageByLike(p);
                break;
            case "all":
            default:
                page = articleManage.page(p);
        }
        return page;
    }

    public IPage<Article> searchByHighLigh(String word, int curPage, int size) {
        return articleEsManage.searchByHighLigh(word, curPage, size);
    }

    public List<String> searchTips(String prefixWord, String indexName, int size) {
        return articleEsManage.searchTips(prefixWord, indexName, size);
    }

    private DocEsDTO buildDocEsDTO(ArticleDetailCommand detail) {
        DocEsDTO docEsDTO = new DocEsDTO();
        BeanUtils.copyProperties(docEsDTO, detail);
        return docEsDTO;
    }

    public Article getById(Integer articleId) {
        return articleManage.getById(articleId);
    }
}

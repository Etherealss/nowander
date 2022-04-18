package com.nowander.blog.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nowander.blog.manage.ArticleEsManage;
import com.nowander.blog.manage.ArticleManage;
import com.nowander.blog.mapper.ArticleContentMapper;
import com.nowander.blog.pojo.dto.ArticleDetailDTO;
import com.nowander.blog.pojo.po.ArticleContent;
import com.nowander.blog.pojo.vo.ArticleDetailVO;
import com.nowander.blog.pojo.dto.DocEsDTO;
import com.nowander.blog.mapper.ArticleMapper;
import com.nowander.blog.pojo.po.Article;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    public void save(ArticleDetailVO entity) {
        articleManage.save(entity);
        articleEsManage.save(buildDocEsDTO(entity));
    }

    public boolean removeById(Serializable id) {
        return articleManage.removeById(id);
    }

    public void update(ArticleDetailVO entity) {
        if (!StrUtil.isBlank(entity.getContent())) {
            articleManage.updateContent(entity);
        }
        articleManage.updateById(entity);
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

    private DocEsDTO buildDocEsDTO(ArticleDetailVO detail) {
        DocEsDTO docEsDTO = new DocEsDTO();
        BeanUtils.copyProperties(docEsDTO, detail);
        return docEsDTO;
    }

    public Article getById(Integer articleId) {
        return articleManage.getById(articleId);
    }
}

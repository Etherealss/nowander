package com.nowander.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nowander.common.pojo.po.Article;
import com.nowander.common.pojo.vo.Msg;

import java.util.List;

/**
 * @author wtk
 * @since 2022-01-05
 */
public interface ArticleService extends IService<Article> {

    Msg<IPage<Article>> page(int curPage, int size, String orderBy);

    /**
     * 搜索补全
     * @param prefixWord
     * @param indexName ES索引名
     * @param size 数量
     * @return
     */
    Msg<List<String>> searchTips(String prefixWord, String indexName, int size);

    /**
     * 高亮、多字段 搜索
     * @param word
     * @param from 起始记录
     * @param size 记录数
     * @return
     */
    Msg<IPage<Article>> searchByHighLigh(String word, int from, int size);
}
package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.utils.DataMap;

/**
 * @ClassName ArticleService.java
 * @Description TODO
 * @createTime 2023/08/05
 */
public interface ArticleService {
    void insertTags(String newTag, String articleGrade);

    DataMap insertArticle(Article article);

    DataMap getArticleManagement(Integer row, Integer currentPage);
}

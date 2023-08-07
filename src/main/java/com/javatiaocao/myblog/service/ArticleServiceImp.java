package com.javatiaocao.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tags;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ArticleServiceImp.java
 * @Description TODO
 * @createTime 2023/08/05
 */
@Service
public class ArticleServiceImp implements  ArticleService{
    @Resource
    private ArticleMapper articleMapper;
    @Override
    public void insertTags(String tagName, String tagSize) {
        if (!IsTagExist(tagName)){
            articleMapper.insertTags(tagName, tagSize);
        }
    }

    @Override
    public DataMap insertArticle(Article article) {
        if (StringUtil.BLANK.equals(article.getArticleUrl())){
            article.setArticleUrl("www.javatiaozao.com" + "/article/" + article.getArticleId());
        }
        articleMapper.insertArticle(article);

        // data to front end. it needs articleUrl, articleTitle, updateDate and author
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articleTitle", article.getArticleTitle());
        jsonObject.put("updateDate", article.getUpdateDate());
        jsonObject.put("articleUrl", article.getArticleUrl());
        jsonObject.put("author", article.getAuthor());

        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap getArticleManagement(Integer row, Integer currentPage) {
        List<Article> articles = articleMapper.selectAllArticles();
        PageHelper.startPage(currentPage, row);
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);

        // need the [Data][pageInfo][pageSize] pageNum, pages, total
        // result -> need the id, articleId, articleTitle, publishDate, articleCategories, visitorNum(now just give a number)
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for (Article article : articles) {
            jsonObject = new JSONObject();
            jsonObject.put("id", article.getId());
            jsonObject.put("articleId", article.getArticleId());
            jsonObject.put("articleTitle", article.getArticleTitle());
            jsonObject.put("publishDate", article.getPublishDate());
            jsonObject.put("articleCategories", article.getArticleCategories());
            jsonObject.put("visitorNum", 999);
            jsonArray.add(jsonObject);
        }
        resultJson.put("result", jsonArray);
        JSONObject page = new JSONObject();
        page.put("pageSize", articlePageInfo.getPageSize());
        page.put("pageNum", articlePageInfo.getPageNum());
        page.put("pages", articlePageInfo.getPages());
        page.put("total", articlePageInfo.getTotal());
        resultJson.put("pageInfo", page);
        return DataMap.success().setData(resultJson);
    }

    private boolean IsTagExist(String tagName){
        List<Tags> tags = articleMapper.selectTagByTagName(tagName);
        if (tags.size() != 0){
            return true;
        }
        return false;
    }
}

package com.javatiaocao.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tags;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ArticleServiceImp implements ArticleService{
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
    public DataMap getArticleManagement(int row, int currentPage) {
        PageHelper.startPage(currentPage, row);
        List<Article> articles = articleMapper.selectAllArticles();
        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        articlePageInfo.setPageSize(10);
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

    @Override
    @Transactional
    public DataMap deleteArticle(int id) {
        if (!IsArticleExist(id)){
            return DataMap.fail(CodeType.DELETE_ARTICLE_FAIL);
        }
        //still need to update the nextArticleId and lastArticleId for other articles.
        Article article = articleMapper.selectArticleById(id);
        long nextArticleId = article.getNextArticleId();
        long lastArticleId = article.getLastArticleId();

        Article nextArticle = articleMapper.selectArticleByArticleId(nextArticleId);
        Article lastArticle = articleMapper.selectArticleByArticleId(lastArticleId);
        if (lastArticle == null){
            //this article is the first article and we want to remove it.
            if (nextArticle == null){
                //next article is null, we only have one article and we remove it. we do not need to do anything.
            }else{
                //next article is not null.
                nextArticle.setLastArticleId(0);
                articleMapper.updateArticle(nextArticle);
            }
        }else {
            if (nextArticle == null){
                //last article will be the real last article.
                lastArticle.setNextArticleId(0);
                articleMapper.updateArticle(lastArticle);
            }else{
                //next article is not null.
                nextArticle.setLastArticleId(lastArticleId);
                lastArticle.setNextArticleId(nextArticleId);
                articleMapper.updateArticle(nextArticle);
                articleMapper.updateArticle(lastArticle);
            }
        }
        articleMapper.deleteArticleById(id);
        return DataMap.success();
    }

    private boolean IsTagExist(String tagName){
        List<Tags> tags = articleMapper.selectTagByTagName(tagName);
        if (tags.size() != 0){
            return true;
        }
        return false;
    }

    private boolean IsArticleExist(int id){
        Article article = articleMapper.selectArticleById(id);
        if (article != null){
            return true;
        }
        return false;
    }
}

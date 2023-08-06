package com.javatiaocao.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tags;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private boolean IsTagExist(String tagName){
        List<Tags> tags = articleMapper.selectTagByTagName(tagName);
        if (tags.size() != 0){
            return true;
        }
        return false;
    }
}

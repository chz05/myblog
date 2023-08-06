package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @ClassName AriticleController.java
 * @Description TODO
 * @createTime 2023/08/04
 */
@RestController
@Slf4j
public class ArticleController {

    @Resource
    private UserService userService;

    @Resource
    private ArticleService articleService;

    /**
     * @ClassName AriticleController.java
     * @MethodName publishArticle
     * @Description
     * @createTime 2023/08/04
     */
    @PostMapping("/publishArticle")
    @Transactional
    public String publishArticle(Article article, @RequestParam("articleGrade") String articleGrade,
                                 HttpServletRequest request){
        //from the article, we can get the articleTitle, articleContent, articleType, articleCategories
        try{
            //if request.getParameter("id") is null or "" means the article is new article
            //else the article has appeared before, we need to change something in the article.
            //we set the articleId by using the LongTime by timeUtil.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String phone = authentication.getName();
            User user = userService.selectByPhoneNumber(phone);

            article.setAuthor(user.getUsername());
            //solve tags
            String[] tagsValue = request.getParameterValues("articleTagsValue");
            String[] newTags = new String[tagsValue.length + 1];
            for (int i = 0; i < tagsValue.length; i++) {
                //preprocess the tagsValue
                newTags[i] = tagsValue[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("$nbsp", StringUtil.BLANK)
                        .replaceAll("\\s", StringUtil.BLANK).trim();
            }
            newTags[newTags.length - 1] = article.getArticleType();
            //insert those tags.
            for (int i = 0; i < newTags.length; i++) {
                articleService.insertTags(newTags[i], articleGrade);
            }
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String articleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));

            article.setArticleTabloid(articleHtmlContent + "...");
            //solve articles
            //old article.
            String id = request.getParameter("id");
            if (id != null){
                // update article

            } else{
                // new article
                // set articleID
                TimeUtil timeUtil = new TimeUtil();
                String formatDateForThree = timeUtil.getFormatDateForThree();
                article.setPublishDate(formatDateForThree);
                article.setUpdateDate(formatDateForThree);
                article.setArticleId(timeUtil.getLongTime());
                article.setOriginalAuthor(user.getUsername());

                StringBuilder stringBuilder = new StringBuilder();
                for (String tagValue : tagsValue){
                    stringBuilder.append(tagValue);
                    stringBuilder.append(" ");
                }

                article.setArticleTags(stringBuilder.toString());
                DataMap data = articleService.insertArticle(article);

                return JsonResult.build(data).toJSON();
            }

        }catch (Exception e){
            log.error("publish Article Exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

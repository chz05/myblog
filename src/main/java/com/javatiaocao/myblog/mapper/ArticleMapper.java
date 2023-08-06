package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ArticleMapper.java
 * @Description TODO
 * @createTime 2023/08/05
 */
@Mapper
@Repository
public interface ArticleMapper {
    List<Tags> selectTagByTagName(String tagName);

    void insertTags(@Param("tagName") String tagName, @Param("tagSize") String tagSize);

    void insertArticle(Article article);
}

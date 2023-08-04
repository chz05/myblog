package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Categories;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CategoriesMapper.java
 * @Description TODO
 * @createTime 2023/08/03
 */
@Mapper
@Repository
public interface CategoriesMapper {
    List<Categories> selectAllCategories();

    Categories selectCategoryByCategoryName(String CategoryName);

    void insertCategory(String categoryName);

    List<Article> selectArticlesByCategoryName(String articleCategories);

    void DeleteCategory(String categoryName);
}

package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Categories;
import org.apache.ibatis.annotations.Mapper;
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
}

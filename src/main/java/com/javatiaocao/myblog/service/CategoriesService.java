package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoriesService.java
 * @Description TODO
 * @createTime 2023/08/03
 */

public interface CategoriesService {
    DataMap getAllCategories();

    DataMap addCategory(String categoryName);

    DataMap removeCategory(String categoryName);

    DataMap findCategoriesName();
}

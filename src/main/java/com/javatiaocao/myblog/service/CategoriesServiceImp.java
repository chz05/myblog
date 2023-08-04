package com.javatiaocao.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.CategoriesMapper;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.utils.DataMap;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoriesServiceImp.java
 * @Description TODO
 * @createTime 2023/08/03
 */
@Service
public class CategoriesServiceImp implements CategoriesService{
    @Resource
    private CategoriesMapper categoriesMapper;
    @Override
    public DataMap getAllCategories() {
        List<Categories> categories = categoriesMapper.selectAllCategories();

        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Categories category : categories) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName", category.getCategoryName());
            jsonArray.add(jsonObject);
        }
        resultJson.put("result", jsonArray);
        return DataMap.success().setData(resultJson);
    }

    @Override
    public DataMap addCategory(String categoryName) {
        //if category exist, we do not need to add it
        if (categoriesMapper.selectCategoryByCategoryName(categoryName) != null){
            return DataMap.fail(CodeType.CATEGORY_EXIST);
        }
        categoriesMapper.insertCategory(categoryName);
        Categories newCategory = categoriesMapper.selectCategoryByCategoryName(categoryName);
        return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategory);
    }

    @Override
    public DataMap removeCategory(String categoryName) {
        //if category does not exist, we do not need to delete it
        if (categoriesMapper.selectCategoryByCategoryName(categoryName) == null){
            return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
        }
        //check the category if have articles or not
        if (categoriesMapper.selectArticlesByCategoryName(categoryName).size() != 0){
            return DataMap.fail(CodeType.CATEGORY_HAS_ARTICLE);
        }
        categoriesMapper.DeleteCategory(categoryName);
        return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
    }
}

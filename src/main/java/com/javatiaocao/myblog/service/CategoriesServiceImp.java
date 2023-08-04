package com.javatiaocao.myblog.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javatiaocao.myblog.mapper.CategoriesMapper;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.utils.DataMap;
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
}

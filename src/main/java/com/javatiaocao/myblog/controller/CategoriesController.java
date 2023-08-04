package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName CatergoriesController.java
 * @Description TODO
 * @createTime 2023/08/03
 */
@RestController
@Slf4j
public class CategoriesController {
    @Resource
    private CategoriesService categoriesService;

    @GetMapping("/getArticleCategories")
    public String getArticleCatergories(){
        try{
            DataMap data = categoriesService.getAllCategories();
            System.out.println(data);
            return JsonResult.build(data).toJSON();
        }catch(Exception e){
            log.error("CategoriesController getArticleCategories exception", e);
        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}

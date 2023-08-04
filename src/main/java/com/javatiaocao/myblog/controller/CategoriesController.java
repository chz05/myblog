package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * @MethodName updateCategory
     * @Description Add one category or delete one category. if type = 1, add. if type = 2, remove one.
     * @createTime 2023/08/03
     */
    @Transactional
    @PostMapping("/updateCategory")
    public String updateCategory(String categoryName, int type){
        try{
            //401 success add. if we wanna add.
            //403 success remove
            DataMap data = null;
            if (type == 1){
                data = categoriesService.addCategory(categoryName);
            }else if (type == 2){
                data = categoriesService.removeCategory(categoryName);
            }
            return JsonResult.build(data).toJSON();
        }catch (Exception e){
            log.error("CategoriesController updateCategory exception", e);
        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @GetMapping("/findCategoriesName")
    public String findCategoriesName(){
        try{
            DataMap data = null;
            data = categoriesService.findCategoriesName();
            return JsonResult.build(data).toJSON();
        }catch (Exception e){
            log.error("CategoriesController findCategoriesName exception", e);
        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }



}

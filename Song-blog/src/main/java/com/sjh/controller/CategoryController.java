package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "获取文章分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }

}

package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "增加对应文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return articleService.updateViewCount(id);
    }

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获取热门文章")
    public ResponseResult hotArticleList() { // 热门文章列表
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "获取文章列表")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取对应文章详情")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }






















}

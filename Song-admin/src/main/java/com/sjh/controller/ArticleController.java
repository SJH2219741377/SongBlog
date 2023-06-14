package com.sjh.controller;

import com.sjh.domain.ResponseResult;
import com.sjh.domain.dto.AddArticleDto;
import com.sjh.service.ArticleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    public ResponseResult add(@RequestBody AddArticleDto addArticleDto) { // 写博文
        return articleService.add(addArticleDto);
    }

}

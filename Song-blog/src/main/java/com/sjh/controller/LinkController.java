package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "获取友联列表")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

}

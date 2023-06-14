package com.sjh.controller;

import com.sjh.domain.ResponseResult;
import com.sjh.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        return uploadService.uploadImg(multipartFile);
    }

}

package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
public class UploadController {

    @Resource
    UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(businessName = "上传头像")
    public ResponseResult uploadImg(MultipartFile img) {
        if (Objects.isNull(img)) throw new SystemException(AppHttpCodeEnum.FILE_CANNOT_NULL);
        return uploadService.uploadImg(img);
    }

}

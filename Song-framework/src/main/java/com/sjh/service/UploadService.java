package com.sjh.service;

import com.sjh.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 宋佳豪
 * @version 1.0
 */
public interface UploadService {

    ResponseResult uploadImg(MultipartFile img);

}

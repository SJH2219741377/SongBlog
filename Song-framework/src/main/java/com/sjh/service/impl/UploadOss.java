package com.sjh.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sjh.domain.ResponseResult;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.UploadService;
import com.sjh.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Data
@Service
@ConfigurationProperties(prefix = "oss")
public class UploadOss implements UploadService {

    // OSS上传凭证
    String accessKey;
    String secretKey;
    String bucket;
    String cdn;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        // 判断文件类型
        String originalFilename = img.getOriginalFilename();
        if (!originalFilename.endsWith(".png")) throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);

        // 修改文件路径,统一管理,并上传OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = ossUpload(img, filePath);
        return ResponseResult.okResult(url);
    }

    /**
     * 返回上传文件的url <br>示例: http://rqsotnt5k.hn-bkt.clouddn.com/testPhoto
     * @param file 图片文件
     * @param filePath 图片名称
     * @return url
     */
    private String ossUpload(MultipartFile file, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //返回文件的访问路径
                return cdn + key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


}

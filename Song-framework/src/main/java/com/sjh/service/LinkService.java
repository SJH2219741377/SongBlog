package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author 宋佳豪
 * @since 2023-02-11 03:41:19
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

}


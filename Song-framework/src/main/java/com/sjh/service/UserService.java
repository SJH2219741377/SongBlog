package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.User;

/**
 * @author 宋佳豪
 * @version 1.0
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

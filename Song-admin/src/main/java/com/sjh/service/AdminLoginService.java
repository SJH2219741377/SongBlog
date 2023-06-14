package com.sjh.service;

import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.User;

/**
 * @author 宋佳豪
 * @version 1.0
 */
public interface AdminLoginService {

    ResponseResult login(User user);

    ResponseResult logout();

}

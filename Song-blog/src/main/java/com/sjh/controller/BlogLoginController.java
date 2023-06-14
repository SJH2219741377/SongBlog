package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.User;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
public class BlogLoginController {

    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    /**
     * 注意, 调用此接口后会清除token,前端需要清除缓存
     */
    @PostMapping("/logout")
    @SystemLog(businessName = "登出")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }

}

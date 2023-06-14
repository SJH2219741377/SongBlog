package com.sjh.controller;

import com.sjh.annotation.SystemLog;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.User;
import com.sjh.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "修改用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }

}

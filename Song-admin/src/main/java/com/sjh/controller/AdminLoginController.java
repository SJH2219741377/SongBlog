package com.sjh.controller;

import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.LoginUser;
import com.sjh.domain.entity.Menu;
import com.sjh.domain.entity.User;
import com.sjh.domain.vo.AdminUserInfoVo;
import com.sjh.domain.vo.RoutersVo;
import com.sjh.domain.vo.UserInfoVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.AdminLoginService;
import com.sjh.service.MenuService;
import com.sjh.service.RoleService;
import com.sjh.utils.BeanCopyUtils;
import com.sjh.utils.SecurityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@RestController
public class AdminLoginController {

    @Resource
    AdminLoginService adminLoginService;

    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return adminLoginService.logout();
    }

    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        // 获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        // 根据用户id查询角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(user.getId());
        // 根据用户id查询权限信息
        List<String> permissions = menuService.selectPermByUserId(user.getId());
        // 获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        // 封装返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions, roles, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        // 查询此用户的menu, 类型是tree
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        // 封装返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}

package com.sjh.service.impl;

import com.sjh.constants.SystemConstants;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.LoginUser;
import com.sjh.domain.entity.User;
import com.sjh.domain.vo.PageVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.AdminLoginService;
import com.sjh.utils.JwtUtil;
import com.sjh.utils.RedisCache;
import com.sjh.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 获取userId, 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 存入redis
        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN_KEY + userId, loginUser);
        // 封装token
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取当前登录用户id
        Long userId = SecurityUtils.getUserId();
        // 删除redis中对应key的value
        redisCache.deleteObject(SystemConstants.ADMIN_LOGIN_KEY + userId);
        return ResponseResult.okResult();
    }

}

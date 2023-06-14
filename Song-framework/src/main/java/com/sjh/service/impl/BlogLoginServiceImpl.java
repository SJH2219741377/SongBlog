package com.sjh.service.impl;

import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.LoginUser;
import com.sjh.domain.entity.User;
import com.sjh.domain.vo.BlogUserLoginVo;
import com.sjh.domain.vo.UserInfoVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.service.BlogLoginService;
import com.sjh.utils.BeanCopyUtils;
import com.sjh.utils.JwtUtil;
import com.sjh.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
        }
        // 获取userId, 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);
        // 把token和userInfo封装并返回
        // 把user转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo loginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(loginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取token 解析获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userId
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }


}

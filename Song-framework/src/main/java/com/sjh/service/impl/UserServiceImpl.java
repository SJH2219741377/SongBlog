package com.sjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.domain.ResponseResult;
import com.sjh.domain.entity.User;
import com.sjh.domain.vo.UserInfoVo;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import com.sjh.mapper.UserMapper;
import com.sjh.service.UserService;
import com.sjh.utils.BeanCopyUtils;
import com.sjh.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-02-23 15:07:23
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户id
        Long userId = SecurityUtils.getUserId();
        // 根据id查询用户信息
        User user = getById(userId);
        // 封装成VO
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        if (Objects.isNull(user)) throw new SystemException(AppHttpCodeEnum.NO_EXIST);
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper
                .set(user.getAvatar() != null, User::getAvatar, user.getAvatar())
                .set(user.getNickName() != null, User::getNickName, user.getNickName())
                .set(user.getSex() != null, User::getSex, user.getSex())
                .set(user.getEmail() != null, User::getEmail, user.getEmail())
                .eq(User::getId, user.getId());
        update(null, wrapper); // 第一个参数需要设置null，这样就只会更新set的字段
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 非空判断
        if (!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NO_EXIST);
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NO_EXIST);
        }
        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NO_EXIST);
        }
        if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NO_EXIST);
        }
        // 判断是否已经存在相同的
        if (userNameExist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 加密密码
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return userMapper.selectCount(wrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName, nickName);
        return userMapper.selectCount(wrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return userMapper.selectCount(wrapper) > 0;
    }

}

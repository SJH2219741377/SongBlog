package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:45:07
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

}


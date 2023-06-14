package com.sjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjh.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:45:07
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

}


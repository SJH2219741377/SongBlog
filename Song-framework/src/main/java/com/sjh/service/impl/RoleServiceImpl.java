package com.sjh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.entity.Role;
import com.sjh.mapper.RoleMapper;
import com.sjh.service.RoleService;
import com.sjh.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:45:07
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否为超管, 如果是,则返回集合中只需有admin角色即可
        if (Boolean.TRUE.equals(SecurityUtils.isAdmin())) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add(SystemConstants.ROLE_KEY_ADMIN);
            return roleKeys;
        }
        // 否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

}


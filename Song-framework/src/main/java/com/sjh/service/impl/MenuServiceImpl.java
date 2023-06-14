package com.sjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjh.constants.SystemConstants;
import com.sjh.domain.entity.Menu;
import com.sjh.mapper.MenuMapper;
import com.sjh.service.MenuService;
import com.sjh.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:37:25
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermByUserId(Long id) {
        // 如果是超管, 返回所有权限
        if (Boolean.TRUE.equals(SecurityUtils.isAdmin())) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.PERMISSION_MENU, SystemConstants.PERMISSION_BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.USER_STATUS_NORMAL);
            List<Menu> list = list(wrapper);
            return list.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        // 否则返回其所具有的权限
        return getBaseMapper().selectPermissionsByUserId();
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        // 判断是否为超管
        if (Boolean.TRUE.equals(SecurityUtils.isAdmin())) {
            // 获取所有符合要求的menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            // 获取当前用户所具有的menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree
        // 先找到第一层的菜单, 然后去找它们的子菜单设置到children属性中
        List<Menu> menuTree;
        menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    /**
     * 构建一颗 Menu Tree
     * @param menus menu集合,用于构建tree的基底
     * @param parentId 第一层menu的父id标识
     * @return Menu Tree
     */
    private List<Menu> builderMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuTree;
        menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入menu的子menu集合 , 此方法可能会递归
     *
     * @param menu  传入的menu
     * @param menus 传入的menu集合
     * @return 传入的menu的子menu
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList;
        childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }

}


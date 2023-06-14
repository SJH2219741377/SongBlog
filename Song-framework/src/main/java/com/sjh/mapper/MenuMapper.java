package com.sjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjh.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:37:25
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermissionsByUserId();

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

}


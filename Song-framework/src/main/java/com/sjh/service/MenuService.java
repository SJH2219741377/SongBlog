package com.sjh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjh.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author 宋佳豪
 * @since 2023-03-09 15:37:25
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

}


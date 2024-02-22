package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author qianye
 * @since 2024-02-22 11:01:53
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    /**
     * 根据用户id获取路由
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}


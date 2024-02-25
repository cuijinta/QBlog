package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Menu;
import com.lut.pojo.vo.MenuVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;

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

    /**
     * 新增菜单
     * @param menu 菜单实体
     * @return
     */
    Result add(Menu menu);

    /**
     * 根据id获取菜单
     * @param id 菜单id
     * @return
     */
    Result<MenuVO> getMenu(Long id);

    /**
     * 获取菜单列表
     * @param menuName 菜单名
     * @param status 菜单状态
     * @return
     */
    Result pageMenuList(String menuName, String status);

    /**
     * 更新菜单
     * @param menu 菜单实体
     * @return
     */
    Result update(Menu menu);

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 获取菜单树
     * @return
     */
    Result getSelectList();
}


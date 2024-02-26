package com.lut.controller;

import com.lut.pojo.entity.Menu;
import com.lut.pojo.vo.MenuVO;
import com.lut.result.Result;
import com.lut.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 浅夜
 * @Description 菜单控制器
 * @DateTime 2024/2/25 19:27
 **/
@RestController
@RequestMapping("/system")
@Api(tags = "菜单相关")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 新增菜单
     * @param menu 菜单实体
     * @return
     */
    @PostMapping("/menu")
    public Result add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    /**
     * 根据id获取菜单
     * @param id 菜单id
     * @return
     */
    @GetMapping("/menu/{id}")
    public Result<MenuVO> get(@PathVariable Long id) {
        return menuService.getMenu(id);
    }

    /**
     * 获取菜单列表
     * @param menuName 菜单名
     * @param status 菜单状态
     * @return
     */
    @GetMapping("/menu/list")
    public Result list(String menuName, String status) {
        return menuService.pageMenuList(menuName, status);
    }

    /**
     * 更新菜单
     * @param menu 菜单实体
     * @return
     */
    @PutMapping("/menu")
    public Result update(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    /**
     * 根据id删除菜单
     * @param ids
     * @return
     */
    @DeleteMapping("/menu/{id}")
    public Result delete(@PathVariable Long[] ids) {
        return menuService.delete(ids);
    }

    @GetMapping("/menu/treeselect")
    public Result getSelectList() {
        return menuService.getSelectList();
    }


    /**
     * 根据角色id获取对应角色的菜单树
     * @param id 角色id
     * @return
     */
    @GetMapping("/menu/roleMenuTreeselect/{id}")
    public Result getSelectListById(@PathVariable Long id) {
        return menuService.getSelectList(id);
    }


}

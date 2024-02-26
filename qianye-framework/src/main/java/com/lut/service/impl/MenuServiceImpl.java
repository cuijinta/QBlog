package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.exception.GlobalException;
import com.lut.mapper.MenuMapper;
import com.lut.pojo.entity.Menu;
import com.lut.pojo.entity.RoleMenu;
import com.lut.pojo.vo.MenuBranchVO;
import com.lut.pojo.vo.MenuVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.MenuService;
import com.lut.service.RoleMenuService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author qianye
 * @since 2024-02-22 11:01:54
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()) {
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    /**
     * 新增菜单
     * @param menu 菜单实体
     * @return
     */
    @Override
    public Result add(Menu menu) {
        boolean save = save(menu);
        return save? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 根据id获取菜单
     * @param id 菜单id
     * @return
     */
    @Override
    public Result<MenuVO> getMenu(Long id) {
        Menu menu = getById(id);
        MenuVO menuVO = BeanCopyUtils.copyBean(menu, MenuVO.class);
        return Result.okResult(menuVO);
    }

    /**
     * 获取菜单列表
     * @param menuName 菜单名
     * @param status 菜单状态
     * @return
     */
    @Override
    public Result pageMenuList(String menuName, String status) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuName), "menu_name", menuName);
        queryWrapper.eq(StringUtils.hasText(status), "status", status);
        queryWrapper.orderByAsc("order_num");
        List<Menu> list = list(queryWrapper);
        return Result.okResult(list);
    }

    /**
     * 更新菜单
     * @param menu 菜单实体
     * @return
     */
    @Override
    public Result update(Menu menu) {
        if(menu.getParentId() == menu.getId()) {
            throw new GlobalException(AppHttpCodeEnum.PARENT_NOT_ALLOW);
        }
        updateById(menu);
        return Result.okResult();
    }

    /**
     * 根据id删除菜单
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Result delete(Long[] ids) {
        int update = menuMapper.deleteBatchIds(Collections.arrayToList(ids));
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_id", ids);
        roleMenuService.remove(queryWrapper);

        return update == ids.length ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 获取菜单树
     * @return
     */
    @Override
    public Result getSelectList() {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu> list = list(queryWrapper); //找出所有的菜单
        List<MenuBranchVO> menuBranchVOList = list.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> {
            MenuBranchVO menuBranchVO = new MenuBranchVO();
            menuBranchVO.setId(menu.getId());
            menuBranchVO.setLabel(menu.getMenuName());
            menuBranchVO.setParentId(menu.getParentId());
            return menuBranchVO;
        }).collect(Collectors.toList());

        List<MenuBranchVO> menuBranchVOList1 = list.stream()
                .filter(menu -> !menu.getParentId().equals(0L))
                .map(menu -> {
                    MenuBranchVO menuBranchVO = new MenuBranchVO();
                    menuBranchVO.setId(menu.getId());
                    menuBranchVO.setLabel(menu.getMenuName());
                    menuBranchVO.setParentId(menu.getParentId());
                    return menuBranchVO;
                }).collect(Collectors.toList());

        menuBranchVOList.stream()
                // 过滤出根菜单项
                .peek(menu -> menu.setChildren(getChildren(menuBranchVOList1, menu))) // 为每个根菜单项设置子菜单
                .collect(Collectors.toList());

        return Result.okResult(menuBranchVOList);
    }

    /**
     * 根据角色id获取对应角色的菜单树
     * @param id 角色id
     * @return
     */
    @Override
    public Result<MenuBranchVO> getSelectList(Long id) {
        //获得该角色的菜单id列表
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        List<RoleMenu> roleMenuList = roleMenuService.list();
        if(CollectionUtils.isEmpty(roleMenuList)) return Result.okResult(new ArrayList<MenuBranchVO>());
        List<Long> menuIds = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        //根据菜单id列表获取菜单列表
        QueryWrapper<Menu> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("id", menuIds);
        List<Menu> menuList = list(queryWrapper1);

        //顶级菜单响应对象列表
        List<MenuBranchVO> voList = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> {
                    MenuBranchVO menuBranchVO = new MenuBranchVO();
                    menuBranchVO.setId(menu.getId());
                    menuBranchVO.setLabel(menu.getMenuName());
                    menuBranchVO.setParentId(menu.getParentId());
                    return menuBranchVO;
                }).collect(Collectors.toList());

        //二级和三级列表
        List<MenuBranchVO> menuBranchVOList1 = menuList.stream()
                .filter(menu -> !menu.getParentId().equals(0L))
                .map(menu -> {
                    MenuBranchVO menuBranchVO = new MenuBranchVO();
                    menuBranchVO.setId(menu.getId());
                    menuBranchVO.setLabel(menu.getMenuName());
                    menuBranchVO.setParentId(menu.getParentId());
                    return menuBranchVO;
                }).collect(Collectors.toList());

        List<MenuBranchVO> res = voList.stream()
                .peek(menu -> menu.setChildren(getChildren(menuBranchVOList1, menu))) // 为每个根菜单项设置子菜单
                .collect(Collectors.toList());

        return Result.okResult(res);
    }

    // 获取子菜单
    private List<MenuBranchVO> getChildren(List<MenuBranchVO> allMenus, MenuBranchVO parent) {
        return allMenus.stream()
                .filter(menu -> menu.getParentId().equals(parent.getId())) // 过滤出子菜单项
                .peek(menu -> menu.setChildren(getChildren(allMenus, menu))) // 递归获取孙子菜单项
                .collect(Collectors.toList());
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}


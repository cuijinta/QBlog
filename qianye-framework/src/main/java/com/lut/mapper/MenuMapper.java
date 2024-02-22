package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author qianye
 * @since 2024-02-22 11:01:51
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}


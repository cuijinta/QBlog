package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-25 23:24:48
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

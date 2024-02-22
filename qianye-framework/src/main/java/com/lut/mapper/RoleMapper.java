package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author qianye
 * @since 2024-02-22 11:27:52
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

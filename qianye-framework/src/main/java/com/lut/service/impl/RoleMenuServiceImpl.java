package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.mapper.RoleMenuMapper;
import com.lut.pojo.entity.RoleMenu;
import com.lut.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-02-25 23:24:50
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

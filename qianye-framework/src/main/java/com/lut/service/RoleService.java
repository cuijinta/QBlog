package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author qianhye
 * @since 2024-02-22 11:27:52
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

}

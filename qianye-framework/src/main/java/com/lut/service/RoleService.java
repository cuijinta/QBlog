package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.dto.RoleDto;
import com.lut.pojo.entity.Role;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author qianhye
 * @since 2024-02-22 11:27:52
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);


    Result<PageVO> pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    /**
     * 改变角色状态
     * @param roleId
     * @param state
     * @return
     */
    Result changeState(Long roleId, String status);

    /**
     * 新增角色
     * @param roleDto 角色请求对象
     * @return
     */
    Result add(RoleDto roleDto);

    /**
     * 更新角色
     * @param roleDto 角色请求对象
     * @return
     */
    Result update(RoleDto roleDto);
}

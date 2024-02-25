package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.mapper.RoleMapper;
import com.lut.mapper.RoleMenuMapper;
import com.lut.pojo.dto.RoleDto;
import com.lut.pojo.entity.Role;
import com.lut.pojo.entity.RoleMenu;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.RoleMenuService;
import com.lut.service.RoleService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author qianye
 * @since 2024-02-22 11:27:53
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    /**
     * 分页获取角色列表
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    @Override
    public Result<PageVO> pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName), Role::getRoleName, roleName);
        queryWrapper.eq(StringUtils.hasText(roleName), Role::getStatus, status);

        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        return Result.okResult(new PageVO(page.getRecords(), page.getTotal()));
    }

    /**
     * 改变角色状态
     * @param roleId 角色id
     * @param status 状态
     * @return
     */
    @Override
    public Result changeState(Long roleId, String status) {
        Role role = getById(roleId);
        role.setStatus(status);
        int update = roleMapper.updateById(role);
        return update > 0 ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 新增角色
     * @param roleDto 角色请求对象
     * @return
     */
    @Override
    @Transactional
    public Result add(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        roleMapper.insert(role); //创建后可以使用它的id
        List<Long> menuIds = roleDto.getMenuIds();
        if(CollectionUtils.isEmpty(menuIds)) return Result.okResult();

        menuIds.forEach(menuId -> {
            roleMenuService.save(new RoleMenu(null, role.getId(), menuId));
        });

        return Result.okResult();
    }

    /**
     * 更新角色
     * @param roleDto 角色请求对象
     * @return
     */
    @Override
    @Transactional
    public Result update(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        roleMapper.updateById(role);

        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", role.getId());
        roleMenuService.remove(queryWrapper);

        List<Long> menuIds = roleDto.getMenuIds();
        if(CollectionUtils.isEmpty(menuIds)) return Result.okResult();
        menuIds.forEach(menuId -> {
            roleMenuService.save(new RoleMenu(null, role.getId(), menuId));
        });
        return Result.okResult();
    }
}

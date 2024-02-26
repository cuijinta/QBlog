package com.lut.controller;

import com.lut.pojo.dto.RoleDto;
import com.lut.pojo.dto.RoleStatusDto;
import com.lut.pojo.vo.MenuBranchVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.RoleService;
import io.swagger.annotations.Api;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 浅夜
 * @Description 角色控制器
 * @DateTime 2024/2/25 20:42
 **/
@RestController
@RequestMapping("/system")
@Api(tags = "角色相关")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/role/list")
    public Result<PageVO> list(Integer pageNum, Integer pageSize, String roleName, String status) {
        return roleService.pageRoleList(pageNum, pageSize, roleName, status);
    }

    @PutMapping("/role/changeStatus")
    public Result changeState(@RequestBody RoleStatusDto roleStatusDto) {
        Long roleId = Long.valueOf(roleStatusDto.getRoleId());
        String status = roleStatusDto.getStatus();
        return roleService.changeState(roleId, status);
    }

    /**
     * 新增角色
     *
     * @param roleDto
     * @return
     */
    @PostMapping("/role")
    public Result add(@RequestBody RoleDto roleDto) {
        return roleService.add(roleDto);
    }

    /**
     * 更新角色
     *
     * @param roleDto 角色请求对象
     * @return
     */
    @PutMapping("/role")
    public Result update(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto);
    }

    /**
     * 根据id删除角色
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/role/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return roleService.delete(ids);
    }

    /**
     * 根据id获取
     * @param id 角色id
     * @return
     */
    @GetMapping("/role/{id}")
    public Result Info(@PathVariable Long id) {
        return roleService.getInfo(id);
    }
}

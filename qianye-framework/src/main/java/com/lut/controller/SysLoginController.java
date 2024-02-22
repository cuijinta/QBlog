package com.lut.controller;

import com.lut.annotation.SystemLog;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.exception.GlobalException;
import com.lut.pojo.entity.LoginUser;
import com.lut.pojo.entity.Menu;
import com.lut.pojo.entity.User;
import com.lut.pojo.vo.AdminUserVO;
import com.lut.pojo.vo.RoutersVO;
import com.lut.pojo.vo.UserInfoVO;
import com.lut.result.Result;
import com.lut.service.LoginService;
import com.lut.service.MenuService;
import com.lut.service.RoleService;
import com.lut.service.UserService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 管理员登录相关接口
 * @Author qianye
 * @Date 2024/2/21 11:22
 * @Version 1.0
 */
@Api(tags = "管理员相关")
@RestController
@RequestMapping
public class SysLoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 管理员登录
     *
     * @param user 管理员用户
     * @return 用户对象
     */
    @PostMapping("sys/login")
//    @SystemLog(businessName = "管理员登录")
    public Result login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            throw new GlobalException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user, SystemConstants.USER_TYPE_ADMIN);
    }

    /**
     * 管理员查看个人用户信息
     *
     * @return userInfoVO
     */
    @GetMapping("getInfo")
    public Result<AdminUserVO> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
        //封装数据返回
        AdminUserVO adminUserVO = new AdminUserVO(perms, roleKeyList, userInfoVO);

        return Result.okResult(adminUserVO);
    }

    @GetMapping("getRouters")
    public Result<RoutersVO> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return Result.okResult(new RoutersVO(menus));
    }


}

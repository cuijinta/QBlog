package com.lut.controller;

import com.lut.annotation.SystemLog;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.pojo.entity.User;
import com.lut.result.Result;
import com.lut.service.LoginService;
import com.lut.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api(tags = "用户登录相关")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user 用户实体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 登录
     * @param user 用户对象
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new GlobalException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    /**
     * 用户退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

    /**
     * 个人信息查询
     * @return
     */
    @GetMapping("/userInfo")
    public Result userInfo() {
        return userService.userInfo();
    }

    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 更新结果
     */
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public Result updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
}

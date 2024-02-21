package com.lut.controller;

import com.lut.annotation.SystemLog;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.exception.GlobalException;
import com.lut.pojo.entity.User;
import com.lut.result.Result;
import com.lut.service.LoginService;
import com.lut.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 管理员登录
     * @param user 管理员用户
     * @return 用户对象
     */
    @PostMapping("sys/login")
//    @SystemLog(businessName = "管理员登录")
    public Result login(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())) {
            throw new GlobalException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user, SystemConstants.USER_TYPE_ADMIN);
    }

    /**
     * 管理员查看个人用户信息
     * @return userInfoVO
     */
    @GetMapping("/sys/getInfo")
    public Result getInfo() {
        return userService.userInfo();
    }

}

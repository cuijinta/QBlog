package com.lut.controller;

import com.lut.constant.AppHttpCodeEnum;
import com.lut.pojo.entity.User;
import com.lut.result.Result;
import com.lut.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if(Objects.isNull(user)) return Result.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        return loginService.login(user);
    }
}

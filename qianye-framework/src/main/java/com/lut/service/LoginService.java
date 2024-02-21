package com.lut.service;

import com.lut.pojo.entity.User;
import com.lut.result.Result;

public interface LoginService {
    /**
     * 用户登录
     * @param user 传入的用户对象
     * @param userType 0-普通用户  1-管理员
     * @return
     */
    Result login(User user, String userType);

    /**
     * 用户登出
     * @return
     */
    Result logout();
}

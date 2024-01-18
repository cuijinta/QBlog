package com.lut.service;

import com.lut.pojo.entity.User;
import com.lut.result.Result;

public interface LoginService {
    /**
     * 用户登录
     * @param user 传入的用户对象
     * @return
     */
    Result login(User user);
}

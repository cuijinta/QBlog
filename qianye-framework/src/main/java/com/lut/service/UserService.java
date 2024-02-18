package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.User;
import com.lut.result.Result;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-02-16 00:46:53
 */
public interface UserService extends IService<User> {

    /**
     * 个人信息查询
     *
     * @return
     */
    Result userInfo();
}

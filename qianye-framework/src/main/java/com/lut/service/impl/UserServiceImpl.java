package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.mapper.UserMapper;
import com.lut.pojo.entity.User;
import com.lut.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-02-16 00:46:53
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

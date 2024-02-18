package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.mapper.UserMapper;
import com.lut.pojo.entity.User;
import com.lut.pojo.vo.UserInfoVO;
import com.lut.result.Result;
import com.lut.service.UserService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-02-16 00:46:53
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 个人信息查询
     * @return
     */
    @Override
    public Result userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVO vo = BeanCopyUtils.copyBean(user,UserInfoVO.class);
        return Result.okResult(vo);
    }
}

package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.mapper.UserMapper;
import com.lut.pojo.entity.User;
import com.lut.pojo.vo.PageVO;
import com.lut.pojo.vo.UserInfoVO;
import com.lut.result.Result;
import com.lut.service.UserService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 用户表(User)表服务实现类
 *
 * @author qianye
 * @since 2024-02-16 00:46:53
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册
     * @param user 用户实体
     * @return 注册结果
     */
    @Override
    public Result register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new GlobalException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new GlobalException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new GlobalException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new GlobalException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new GlobalException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new GlobalException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return Result.okResult();
    }

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

    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 更新结果
     */
    @Override
    public Result updateUserInfo(User user) {
        updateById(user);
        return Result.okResult();
    }

    /**
     * 分页获取用户对象
     * @param pageNum 当当前页数
     * @param pageSize 每页条数
     * @param userName  用户名
     * @param phonenumber 用户电话号码
     * @param status 用户状态
     * @return
     */
    @Override
    public Result<PageVO> pageUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        Page<User> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName, userName);
        queryWrapper.eq(StringUtils.hasText(phonenumber), User::getPhoneNumber, phonenumber);
        queryWrapper.eq(StringUtils.hasText(status), User::getStatus, status);

        page(page, queryWrapper);
        return Result.okResult(new PageVO(page.getRecords(), page.getTotal()));
    }


    private boolean nickNameExist(String nickName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name", nickName);

        return count(queryWrapper) != 0;
    }

    private boolean userNameExist(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);

        return count(queryWrapper) != 0;
    }

}

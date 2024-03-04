package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.mapper.UserMapper;
import com.lut.pojo.dto.UserDto;
import com.lut.pojo.dto.UserStatusDto;
import com.lut.pojo.entity.Role;
import com.lut.pojo.entity.User;
import com.lut.pojo.entity.UserRole;
import com.lut.pojo.vo.PageVO;
import com.lut.pojo.vo.UserInfoVO;
import com.lut.pojo.vo.UserRoleVO;
import com.lut.pojo.vo.UserVO;
import com.lut.result.Result;
import com.lut.service.RoleService;
import com.lut.service.UserRoleService;
import com.lut.service.UserService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;


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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

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
        user.setCreateBy(-1L); //表示自己创建
        user.setUpdateBy(-1L);
        userMapper.insert(user);
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

    /**
     * 新增后台系统用户
     * @param userDto 用户请求对象
     * @return
     */
    @Override
    @Transactional
    public Result addUser(UserDto userDto) {

        if(!StringUtils.hasText(userDto.getUserName())){
            throw new GlobalException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getPassword())){
            throw new GlobalException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getEmail())){
            throw new GlobalException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getNickName())){
            throw new GlobalException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(userDto.getUserName())){
            throw new GlobalException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(userDto.getNickName())){
            throw new GlobalException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(StringUtils.hasText(userDto.getPhoneNumber()) && phoneNumberExist(userDto.getPhoneNumber())){
            throw new GlobalException(AppHttpCodeEnum.PHONE_EXIST);
        }
        if(StringUtils.hasText(userDto.getEmail()) && emailExist(userDto.getEmail())) {
            throw new GlobalException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodePassword);
        int insert = userMapper.insert(user);
        List<Long> roleIds = userDto.getRoleIds();
        if(CollectionUtils.isEmpty(roleIds)) {
            return insert > 0 ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        roleIds.forEach(roleId -> {
            userRoleService.save(new UserRole(null, user.getId(), roleId));
        });

        return insert > 0 ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result changeStatus(UserStatusDto userStatusDto) {
        Long userId = userStatusDto.getUserId();
        String status = userStatusDto.getStatus();
        Long updateBy = SecurityUtils.getUserId();
        User user = getById(userId);
        user.setStatus(status);
        boolean update = updateById(user);
        return update ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getInfo(Long id) {
        User user = getById(id);
        UserVO userVO = BeanCopyUtils.copyBean(user, UserVO.class);

        List<Long> roleIds = new ArrayList<>();
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UserRole> userRoleList = userRoleService.list(queryWrapper);
        if(!CollectionUtils.isEmpty(userRoleList)) {
            roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        }
        List<Role> roleList = new ArrayList<>();
        QueryWrapper<Role> queryWrapper1 = new QueryWrapper<>();
        if(!CollectionUtils.isEmpty(roleIds)) {
            queryWrapper1.in("id", roleIds);
            roleList = roleService.list(queryWrapper1);
        }
        UserRoleVO userRoleVO = new UserRoleVO(userVO, roleIds, roleList);

        return Result.okResult(userRoleVO);
    }

    /**
     * 更新系统用户
     * @param userDto 用户请求实体
     * @return
     */
    @Override
    @Transactional
    public Result updateInfo(UserDto userDto) {
        Long updateBy = SecurityUtils.getUserId();
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        user.setUpdateBy(updateBy);
        user.setUpdateTime(new Date());
        updateById(user);
        List<Long> roleIds = userDto.getRoleIds();
        if(CollectionUtils.isEmpty(roleIds)) return Result.okResult();
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        userRoleService.remove(queryWrapper);

        roleIds.forEach(roleId -> {
            userRoleService.save(new UserRole(null, user.getId(), roleId));
        });
        return Result.okResult();
    }

    @Override
    @Transactional
    public Result delteBatch(Long[] ids) {
        List<Long> idList = (List<Long>) CollectionUtils.arrayToList(ids);
        if (CollectionUtils.isEmpty(idList)) {
            return Result.errorResult(AppHttpCodeEnum.OPERATION_ERROR);
        }
        int update = userMapper.deleteBatchIds(idList);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", idList);
        userRoleService.remove(queryWrapper);
        return update == ids.length ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    private boolean phoneNumberExist(String phoneNumber) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", phoneNumber);

        return count(queryWrapper) != 0;
    }

    private boolean emailExist(String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);

        return count(queryWrapper) != 0;
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

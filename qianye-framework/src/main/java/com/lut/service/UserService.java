package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.dto.UserDto;
import com.lut.pojo.dto.UserStatusDto;
import com.lut.pojo.entity.User;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-02-16 00:46:53
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param user 用户实体
     * @return 注册结果
     */
    Result register(User user);
    /**
     * 个人信息查询
     *
     * @return
     */
    Result userInfo();

    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 更新结果
     */
    Result updateUserInfo(User user);


    /**
     * 分页获取用户对象
     * @param pageNum 当当前页数
     * @param pageSize 每页条数
     * @param userName  用户名
     * @param phonenumber 用户电话号码
     * @param status 用户状态
     * @return
     */
    Result<PageVO> pageUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    /**
     * 新增后台系统用户
     * @param userDto 用户请求对象
     * @return
     */
    Result addUser(UserDto userDto);

    /**
     * 改变用户状态
     * @param userStatusDto 用户状态请求体
     * @return
     */
    Result changeStatus(UserStatusDto userStatusDto);

    Result getInfo(Long id);

    /**
     * 更新系统用户
     * @param userDto 用户请求实体
     * @return
     */
    Result updateInfo(UserDto userDto);


    Result delteBatch(Long[] ids);
}

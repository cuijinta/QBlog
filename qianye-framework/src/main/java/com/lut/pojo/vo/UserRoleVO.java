package com.lut.pojo.vo;

import com.lut.pojo.entity.Role;
import com.lut.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 浅夜
 * @Description 用户角色响应对象
 * @DateTime 2024/2/27 0:02
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVO {
    private UserVO user;
    private List<Long> roleIds;
    private List<Role> roles;
 }

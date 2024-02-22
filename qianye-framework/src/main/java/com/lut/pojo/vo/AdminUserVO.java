package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description 管理员用户对象响应对象
 * @Author qianye
 * @Date 2024/2/22 14:01
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVO {
    private List<String> permissions;

    private List<String> roles;

    private UserInfoVO user;
}

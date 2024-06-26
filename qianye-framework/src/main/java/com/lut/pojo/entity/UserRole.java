package com.lut.pojo.entity;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2024-02-26 22:23:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole {
    @TableId
    private Long id;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;
}

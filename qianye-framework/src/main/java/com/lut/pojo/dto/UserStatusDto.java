package com.lut.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 浅夜
 * @Description 用户状态请求对象
 * @DateTime 2024/2/26 22:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDto {
    private String status;
    private Long userId;
}

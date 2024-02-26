package com.lut.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description todo
 * @Author qianye
 * @Date 2024/2/26 15:49
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleStatusDto {
    private String roleId;
    private String status;
}

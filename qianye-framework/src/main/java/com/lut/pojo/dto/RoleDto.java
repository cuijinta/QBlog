package com.lut.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 浅夜
 * @Description Role请求对象
 * @DateTime 2024/2/25 23:14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String roleName;
    private String roleKey;
    private int roleSort;
    private String status;
    private List<Long> menuIds;
    private String remark;
}

package com.lut.pojo.vo;

import com.lut.pojo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 角色实体响应对象
 * @Author qianye
 * @Date 2024/2/22 14:26
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVO {
    private List<Menu> menus;
}

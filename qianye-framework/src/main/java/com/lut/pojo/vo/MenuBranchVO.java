package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 浅夜
 * @Description 菜单树相应实体
 * @DateTime 2024/2/25 22:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuBranchVO {
    private Long id;
    private String label;
    private Long parentId;
    private List<MenuBranchVO> children;
}

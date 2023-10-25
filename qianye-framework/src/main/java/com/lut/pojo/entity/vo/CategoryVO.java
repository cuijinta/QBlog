package com.lut.pojo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 浅夜
 * @Description 分类响应对象
 * @DateTime 2023/10/25 16:52
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private Long id;
    private String name;
}

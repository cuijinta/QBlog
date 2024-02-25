package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 浅夜
 * @Description 标签响应对象
 * @DateTime 2024/2/25 11:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {
    private Long id;
    /**
     * 标签名
     */
    private String name;
    private String remark;
}

package com.lut.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 浅夜
 * @Description 标签请求对象（标签名/标签备注），用于分页搜索标签
 * @DateTime 2024/2/25 11:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private String name;
    private String remark;
}

package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 浅夜
 * @Description 分页响应对象
 * @DateTime 2023/10/25 17:56
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {
    private List rows;
    private Long total;
}

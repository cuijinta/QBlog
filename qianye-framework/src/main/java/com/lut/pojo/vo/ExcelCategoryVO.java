package com.lut.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @Author 浅夜
 * @Description 导出对象
 * @DateTime 2024/2/25 15:17
 **/
public class ExcelCategoryVO {
    @ExcelProperty("分类名")
    private String name;
    //描述
    @ExcelProperty("描述")
    private String description;

    //状态0:正常,1禁用
    @ExcelProperty("状态0:正常,1禁用")
    private String status;
}

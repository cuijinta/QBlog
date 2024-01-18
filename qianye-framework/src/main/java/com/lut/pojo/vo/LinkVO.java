package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVO {
    private String name;//网站名
    private String logo; //网站logo
    private String description;//网站描述
    private String address;//网站地址
}

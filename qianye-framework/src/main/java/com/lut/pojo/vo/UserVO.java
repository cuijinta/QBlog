package com.lut.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author 浅夜
 * @Description 用户响应对象
 * @DateTime 2024/2/26 0:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String avatar;
    private Date createTime;
    private String email;
    private Long id;
    private String nickName;
    private String phoneNumber;
    private String sex;
    private String status;
    private Long updateBy;
    private Date updateTime;
    private String userName;
}

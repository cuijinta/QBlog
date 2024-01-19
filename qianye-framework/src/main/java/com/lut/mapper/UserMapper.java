package com.lut.mapper;

import com.lut.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author qianye
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-01-18 11:20:37
* @Entity com.lut.pojo.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





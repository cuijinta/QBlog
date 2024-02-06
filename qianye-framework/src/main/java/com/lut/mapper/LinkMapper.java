package com.lut.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lut.pojo.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(Link)表数据库访问层
 *
 * @author qianye
 * @since 2023-10-26 12:00:21
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

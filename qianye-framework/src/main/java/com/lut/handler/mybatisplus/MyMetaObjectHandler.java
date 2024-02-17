package com.lut.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lut.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author 浅夜
 * @Description mybatisplus 字段自动填充
 * @DateTime 2024/2/17 23:55
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动插入方法
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        userId = SecurityUtils.getUserId();
        try {

        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;//表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    /**
     * 自动更新方法
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}

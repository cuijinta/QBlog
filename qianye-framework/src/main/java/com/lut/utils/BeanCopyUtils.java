package com.lut.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 浅夜
 * @Description 拷贝属性配置类
 * @DateTime 2023/10/25 14:38
 **/
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * 单个对象属性拷贝的方法
     * @param source
     * @param clazz
     * @return T vo
     * @param <T>
     */
    public static <T>T copyBean(Object source, Class<T> clazz) {
        //创建目标对象
        T vo = null;
        try {
//            vo = clazz.newInstance();
            //实现属性拷贝
            vo = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, vo);

            //返回结果
            return vo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * 多个对象的属性拷贝方法
     * @param sourceList
     * @param clazz
     * @return <T> List<T>
     * @param <T>
     */
    public static <O,T> List<T> copyBeanList(List<O> sourceList, Class<T> clazz) {
        return sourceList.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}

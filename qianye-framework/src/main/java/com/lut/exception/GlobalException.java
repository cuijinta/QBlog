package com.lut.exception;

import com.lut.constant.AppHttpCodeEnum;

/**
 * @Author 浅夜
 * @Description 自定义异常类
 * @DateTime @DateTime 2024/2/10 22:55
 **/
public class GlobalException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public GlobalException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    
}
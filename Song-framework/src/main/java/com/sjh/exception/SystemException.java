package com.sjh.exception;

import com.sjh.enums.AppHttpCodeEnum;

/**
 * 异常类
 * @author 宋佳豪
 * @version 1.0
 */
public class SystemException extends RuntimeException {

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}

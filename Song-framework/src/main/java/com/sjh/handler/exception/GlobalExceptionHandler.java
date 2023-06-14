package com.sjh.handler.exception;

import com.sjh.domain.ResponseResult;
import com.sjh.enums.AppHttpCodeEnum;
import com.sjh.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 宋佳豪
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("出现异常 -> ", e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        log.error("出现异常 -> ", e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMessage());
    }

}

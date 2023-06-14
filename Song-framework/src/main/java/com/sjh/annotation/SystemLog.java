package com.sjh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志打印注解类
 * @author 宋佳豪
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME) // 保持到运行时
@Target(ElementType.METHOD) // 作用于方法上
public @interface SystemLog {

    /**
     * 描述信息
     */
    String businessName();

}

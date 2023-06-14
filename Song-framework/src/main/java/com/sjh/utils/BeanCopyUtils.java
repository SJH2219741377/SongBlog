package com.sjh.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean拷贝工具类
 *
 * @author 宋佳豪
 * @version 1.0
 */
public class BeanCopyUtils {

    public BeanCopyUtils() {
    }

    /**
     * <b> Content: 实现单个对象属性拷贝<b/>
     *
     * @param source 源对象
     * @param clazz  目标对象字节码
     * @param <V>    V 即返回值类型, <V> 表示泛型方法
     * @return 目标对象
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回目标对象
        return result;
    }

    /**
     * 实现多个对象属性拷贝
     * @param list 源目标对象集合
     * @param clazz 目标对象字节码
     * @param <O> 源对象集合类型
     * @param <V> 返回值集合类型
     * @return 目标集合
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

}

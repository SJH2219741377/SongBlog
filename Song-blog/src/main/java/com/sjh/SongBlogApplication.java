package com.sjh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.sjh.mapper")
@EnableScheduling // 开启定时任务
@EnableSwagger2 // 开启swagger
public class SongBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongBlogApplication.class, args);
    }
}

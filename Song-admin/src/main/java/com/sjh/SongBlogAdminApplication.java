package com.sjh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 宋佳豪
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.sjh.mapper")
public class SongBlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongBlogAdminApplication.class, args);
    }

}

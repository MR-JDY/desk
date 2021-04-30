package com.uni.desk;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.uni.desk.mapper")
public class DeskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeskApplication.class, args);
    }

}

package com.uni.desk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.uni.desk.mapper")
public class DeskApplication {

    public static void main(String[] args) {
        SpringApplication  application = new SpringApplication(DeskApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

}

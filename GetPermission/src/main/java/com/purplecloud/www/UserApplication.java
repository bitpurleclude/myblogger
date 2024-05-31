package com.purplecloud.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.purplecloud.www.mapper")
public class UserApplication {

        public static void main(String[] args) {
            SpringApplication.run(UserApplication.class, args);
        }
}

package com.purplecloud.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.purplecloud.www.mapper")
public class UserPermissionApplication {

        public static void main(String[] args) {
            SpringApplication.run(UserPermissionApplication.class, args);
        }
}

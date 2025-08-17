package com.muggle.cloudpicturebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;


// @SpringBootApplication 是一个组合注解，包含了 @SpringBootConfiguration、@EnableAutoConfiguration 和 @ComponentScan。
@SpringBootApplication
@EnableAsync
// @MapperScan 是MyBatis-Spring提供的注解，用于指定要扫描的MyBatis Mapper接口所在的包。
@MapperScan("com.muggle.cloudpicturebackend.mapper")
// @EnableAspectJAutoProxy 用于启用Spring AOP的自动代理功能
@EnableAspectJAutoProxy(exposeProxy = true)
public class CloudPictureBackendApplication {

    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        SpringApplication.run(CloudPictureBackendApplication.class, args);
    }

}

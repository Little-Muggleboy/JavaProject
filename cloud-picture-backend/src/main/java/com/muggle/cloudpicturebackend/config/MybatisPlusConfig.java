package com.muggle.cloudpicturebackend.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置类
 * 该类用于配置 MyBatis-Plus 的相关功能，包括定义拦截器和指定 Mapper 接口的扫描路径。
 */
@Configuration
// 指定 MyBatis Mapper 接口的扫描路径，Spring 会自动扫描该路径下的所有 Mapper 接口并将其注册到 Spring 容器中
@MapperScan("com.muggle.cloudpicturebackend.mapper")
public class MybatisPlusConfig {

    /**
     * 配置 MyBatis-Plus 拦截器
     * 此拦截器用于增强 MyBatis-Plus 的功能，这里主要是为了实现分页功能。
     *
     * @return MyBatis-Plus 拦截器实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建一个 MyBatis-Plus 拦截器实例
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 向拦截器中添加分页插件
        // PaginationInnerInterceptor 是 MyBatis-Plus 提供的用于实现分页功能的内部拦截器
        // DbType.MYSQL 指定了数据库类型为 MySQL，这样在生成分页 SQL 时会根据 MySQL 的语法规则来生成
        // 如果配置多个插件，建议将分页插件最后添加，以避免可能出现的冲突
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 如果项目中使用了多数据源，可以不指定具体的数据库类型
        // 但在单数据源的情况下，建议明确指定数据库类型，以确保生成分页 SQL 的准确性

        // 返回配置好的拦截器实例
        return interceptor;
    }
}
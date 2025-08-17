package com.muggle.cloudpicturebackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Spring MVC Json 配置类
 * 该类主要用于对 Spring MVC 中 JSON 数据的处理进行配置，特别是解决 Long 类型数据在转换为 JSON 时可能出现的精度丢失问题。
 * 通过自定义 ObjectMapper 来实现特定的序列化规则。
 */
@JsonComponent
public class JsonConfig {

    /**
     * 创建并配置一个自定义的 ObjectMapper 实例
     * 这个 ObjectMapper 会将 Long 类型和 long 基本类型的数据在转换为 JSON 时序列化为字符串，从而避免前端在处理长整型数据时出现精度丢失的问题。
     *
     * @param builder 用于构建 ObjectMapper 的 Jackson2ObjectMapperBuilder 对象，Spring Boot 提供的工具类，方便创建和配置 ObjectMapper
     * @return 配置好的 ObjectMapper 实例，该实例会被 Spring 容器管理并用于 JSON 数据的序列化和反序列化
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // 使用 Jackson2ObjectMapperBuilder 创建一个 ObjectMapper 实例，并指定不创建 XML 映射器（因为这里处理的是 JSON 数据）
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 创建一个简单的模块，用于注册自定义的序列化器
        SimpleModule module = new SimpleModule();

        // 为 Long 包装类型注册 ToStringSerializer 序列化器
        // 这样在将 Long 类型的数据转换为 JSON 时，会将其序列化为字符串形式
        module.addSerializer(Long.class, ToStringSerializer.instance);

        // 为 long 基本类型注册 ToStringSerializer 序列化器
        // 确保基本类型的 long 在转换为 JSON 时也能序列化为字符串
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // 将自定义模块注册到 ObjectMapper 中，使其生效
        objectMapper.registerModule(module);

        // 返回配置好的 ObjectMapper 实例
        return objectMapper;
    }
}
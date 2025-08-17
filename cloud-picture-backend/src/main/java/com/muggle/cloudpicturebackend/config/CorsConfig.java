package com.muggle.cloudpicturebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置类
 * 该类实现了 WebMvcConfigurer 接口，用于配置全局的跨域请求处理规则。
 * 跨域资源共享（CORS）是一种机制，允许浏览器在跨域请求时，服务器能够控制哪些请求可以被接受。
 * 通过该配置类，我们可以对所有的请求设置统一的跨域处理规则。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 重写 addCorsMappings 方法，用于添加跨域映射规则
     * @param registry 用于注册跨域映射的 CorsRegistry 对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求，即对项目中的所有接口都应用跨域配置
        // 这里使用 "/**" 表示匹配所有的 URL 路径
        registry.addMapping("/**")
                // 允许发送 Cookie
                // 当浏览器进行跨域请求时，如果需要携带 Cookie 信息，需要将该属性设置为 true
                // 注意：当设置为 true 时，allowedOriginPatterns 不能使用 "*"，而需要使用具体的域名或域名模式
                .allowCredentials(true)
                // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                // 使用 allowedOriginPatterns("*") 表示允许来自任何域名的请求
                // 这种配置适用于开发和测试环境，在生产环境中建议使用具体的域名列表
                .allowedOriginPatterns("*")
                // 允许的请求方法
                // 这里列出了常见的 HTTP 请求方法，包括 GET、POST、PUT、DELETE 和 OPTIONS
                // OPTIONS 请求通常用于预检请求，用于检查服务器是否允许跨域请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的请求头
                // 使用 "*" 表示允许所有的请求头
                // 这样客户端可以在请求中携带任意的请求头信息
                .allowedHeaders("*")
                // 暴露的响应头
                // 使用 "*" 表示允许客户端访问所有的响应头信息
                // 客户端可以通过 JavaScript 访问这些响应头的值
                .exposedHeaders("*");
    }
}
package com.muggle.cloudpicturebackend.common;

import com.muggle.cloudpicturebackend.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 全局响应封装类，用于统一封装服务端向客户端返回的响应数据。
 * 该类使用泛型，可根据不同的业务场景封装不同类型的数据。
 * 同时实现了 Serializable 接口，允许对象在网络中传输或持久化存储。
 *
 * @param <T> 响应数据的类型，根据具体业务而定
 */
@Data
public class BaseResponse<T> implements Serializable {

    // 响应状态码，用于标识请求的处理结果
    private int code;

    // 响应携带的数据，具体类型由泛型 T 决定，可以是实体类、集合等
    private T data;

    // 响应消息，用于对响应状态进行详细说明
    private String message;

    /**
     * 全参构造函数，用于创建一个包含状态码、响应数据和响应消息的响应对象。
     *
     * @param code    响应状态码
     * @param data    响应携带的数据
     * @param message 响应消息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 双参构造函数，用于创建一个包含状态码和响应数据的响应对象，响应消息默认为空字符串。
     *
     * @param code 响应状态码
     * @param data 响应携带的数据
     */
    public BaseResponse(int code, T data) {
        // 调用全参构造函数，将消息设置为空字符串
        this(code, data, "");
    }

    /**
     * 单参构造函数，用于根据错误码对象创建响应对象，响应数据默认为 null。
     *
     * @param errorCode 错误码对象，包含错误状态码和错误消息
     */
    public BaseResponse(ErrorCode errorCode) {
        // 调用全参构造函数，从错误码对象中获取状态码和消息，数据设置为 null
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
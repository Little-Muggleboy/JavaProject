package com.muggle.cloudpicturebackend.exception;

import lombok.Getter;

/**
 * 自定义业务异常类，继承自 RuntimeException，用于在业务逻辑中抛出特定的业务错误。
 * 该类包含一个错误码字段，方便在异常处理时根据错误码进行不同的处理。
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码，用于标识不同类型的业务错误，方便统一管理和处理。
     */
    private final int code;

    /**
     * 构造一个新的业务异常，使用指定的错误码和错误消息。
     *
     * @param code    错误码，用于标识异常的类型。
     * @param message 详细的错误消息，用于描述异常发生的原因。
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造一个新的业务异常，使用预定义的错误码枚举。
     *
     * @param errorCode 预定义的错误码枚举，包含错误码和错误消息。
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 构造一个新的业务异常，使用预定义的错误码枚举和自定义的错误消息。
     * 当预定义的错误消息不足以描述异常情况时，可以使用此构造函数提供更详细的信息。
     *
     * @param errorCode 预定义的错误码枚举，包含错误码。
     * @param message   自定义的详细错误消息，用于描述异常发生的原因。
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
package com.muggle.cloudpicturebackend.exception;

/**
 * 异常处理工具类，提供了一系列静态方法，用于根据指定条件抛出相应的业务异常。
 * 该工具类可以帮助开发者在业务逻辑中更方便地处理异常抛出逻辑，提高代码的可读性和可维护性。
 */
public class ThrowUtils {

    /**
     * 当指定条件为 true 时，抛出指定的运行时异常。
     * 此方法可用于在业务逻辑中根据条件灵活抛出各种运行时异常。
     *
     * @param condition        用于判断是否抛出异常的条件。如果该条件为 true，则抛出异常；否则不做任何操作。
     * @param runtimeException 当条件成立时要抛出的运行时异常对象。
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 当指定条件为 true 时，抛出基于指定错误码的业务异常。
     * 该方法会根据传入的错误码创建一个 `BusinessException` 实例并抛出。
     *
     * @param condition 用于判断是否抛出异常的条件。如果该条件为 true，则抛出异常；否则不做任何操作。
     * @param errorCode 预定义的错误码枚举，包含了错误的状态码和默认错误信息，用于创建业务异常。
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 当指定条件为 true 时，抛出基于指定错误码和自定义错误信息的业务异常。
     * 该方法允许开发者在使用预定义错误码的同时，提供更详细的自定义错误信息。
     *
     * @param condition 用于判断是否抛出异常的条件。如果该条件为 true，则抛出异常；否则不做任何操作。
     * @param errorCode 预定义的错误码枚举，包含了错误的状态码，用于创建业务异常。
     * @param message   自定义的错误信息，将覆盖错误码枚举中的默认错误信息，用于更准确地描述异常情况。
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
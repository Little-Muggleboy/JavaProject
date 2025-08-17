package com.muggle.cloudpicturebackend.common;

import lombok.Data;

/**
 * 通用的删除请求类，用于封装删除操作所需的基本信息。
 */
@Data
public class DeleteRequest {
    /**
     * 要删除的数据的唯一标识。
     * 在数据库中，每条记录通常都有一个唯一的 ID，通过这个 ID 可以准确地定位到要删除的记录。
     */
    private Long id;

    /**
     * 序列化版本号。
     * 当该类实现了 Serializable 接口时，用于确保序列化和反序列化过程的兼容性。
     * 在反序列化时，JVM 会根据这个版本号来验证序列化对象和当前类是否兼容。
     * 这里将其设置为 1L，表示该类的初始版本。
     */
    private static final long serialVersionUID = 1L;
}
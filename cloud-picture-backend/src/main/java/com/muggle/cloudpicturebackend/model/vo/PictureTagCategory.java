package com.muggle.cloudpicturebackend.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 响应请求返回图片标签分类列表信息封装类 (脱敏)
 */
@Data
public class PictureTagCategory {

    /**
     * 标签列表
     */
    private List<String> tagList;

    /**
     * 分类列表
     */
    private List<String> categoryList;
}
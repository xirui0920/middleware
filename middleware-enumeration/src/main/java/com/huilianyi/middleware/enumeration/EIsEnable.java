package com.huilianyi.middleware.enumeration;

import lombok.Getter;
import lombok.ToString;

/**
 * EIsEnable.java
 * 是否启用
 *
 * @author : Gooliang Young
 * @date : 2018/4/14 上午11:43
 */
@Getter
@ToString
public enum EIsEnable {
    /**
     * 禁用
     */
    UNABLE(0, "禁用"),
    /**
     * 启用
     */
    ENABLE(1, "启用");

    /**
     * 枚举代码
     */
    private Integer code;
    /**
     * 枚举描述
     */
    private String desc;

    EIsEnable(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

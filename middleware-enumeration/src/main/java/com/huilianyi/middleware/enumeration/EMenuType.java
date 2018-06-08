package com.huilianyi.middleware.enumeration;

import lombok.Getter;
import lombok.ToString;

/**
 * EMenuType.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/1 下午2:31
 */
@Getter
@ToString
public enum EMenuType {
    /**
     * 路径菜单
     */
    PATH(0),
    /**
     * 叶子菜单
     */
    LEAF(1);
    private Integer code;

    EMenuType(Integer code) {
        this.code = code;
    }
}

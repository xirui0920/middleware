package com.huilianyi.middleware.enumeration;

import lombok.Getter;

/**
 * ECRUDType.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/15 下午12:13
 */
@Getter
public enum ECRUDType {
    /**
     * 增
     */
    ADD(),
    /**
     * 删
     */
    DELETE(),
    /**
     * 改
     */
    UPDATE(),
    /**
     * 查
     */
    SELECT()
}

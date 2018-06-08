package com.huilianyi.middleware.enumeration;

import lombok.Getter;

/**
 * EDataStatus.java
 * 数据状态枚举
 *
 * @author : Gooliang Young
 * @date : 2018/4/17 下午10:17
 */
@Getter
public enum EDataStatus {
    /**
     * 新增的数据
     */
    ADDED(0, "新增的数据"),
    /**
     * 修改的数据
     */
    UPDATED(1, "修改的数据"),
    /**
     * 待删除的数据
     */
    DELETE(2, "待删除的数据"),
    /**
     * 已删除的数据
     */
    DELETED(3, "已删除的数据"),
    /**
     * 已同步的数据
     */
    SYNC(4, "已同步的数据");

    /**
     * 枚举代码
     */
    private Integer code;
    /**
     * 枚举描述
     */
    private String desc;

    EDataStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}

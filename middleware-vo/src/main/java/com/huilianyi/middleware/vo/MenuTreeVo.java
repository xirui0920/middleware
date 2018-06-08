package com.huilianyi.middleware.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * MenuTreeVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 下午10:52
 */
@Getter
@Setter
@ToString
public class MenuTreeVo {
    /**
     * 节点id
     */
    private String id;
    /**
     * 父id
     */
    private String pId;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点名称包含图标
     */
    private String nameWithIcon;
    /**
     * 是否展开
     */
    private Boolean open;
    /**
     * 是否被选中
     */
    private boolean checked;
    /**
     * 节点图标
     */
    private String iconSkin;
}

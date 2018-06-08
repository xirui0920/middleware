package com.huilianyi.middleware.po;

import com.huilianyi.middleware.common.CommonPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SystemMenu.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:07
 */
@Getter
@Setter
@ToString
public class SystemMenu extends CommonPo {
    private String parentId;
    private String menuKey;
    private String menuUri;
    private Integer menuType;
    private String menuIcon;
    private Integer sort;
    private String createBy;
}

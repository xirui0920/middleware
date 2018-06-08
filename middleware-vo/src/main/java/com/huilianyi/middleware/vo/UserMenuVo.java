package com.huilianyi.middleware.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * UserMenuVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/1 下午1:41
 */
@Getter
@Setter
@ToString
public class UserMenuVo {
    private String objId;
    private String menuKey;
    private String menuUri;
    private Integer menuType;
    private String menuIcon;
    private List<UserMenuVo> list;
}

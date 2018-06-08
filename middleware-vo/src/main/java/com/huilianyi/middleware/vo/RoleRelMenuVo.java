package com.huilianyi.middleware.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * RoleRelMenuVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/26 下午8:21
 */
@Getter
@Setter
@ToString
public class RoleRelMenuVo {
    private String roleId;
    private List<String> menuIds;
}

package com.huilianyi.middleware.po;

import com.huilianyi.middleware.common.CommonPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SystemRole.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:00
 */
@Getter
@Setter
@ToString
public class SystemRole extends CommonPo {
    private String roleName;
    private String roleNickname;
    private String createBy;
}

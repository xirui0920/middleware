package com.huilianyi.middleware.po;

import com.huilianyi.middleware.common.CommonPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SystemUser.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午4:08
 */
@Getter
@Setter
@ToString
public class SystemUser extends CommonPo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建人
     */
    private String createBy;
}

package com.huilianyi.middleware.po;

import com.huilianyi.middleware.common.CommonPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SystemLog.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:11
 */
@Getter
@Setter
@ToString
public class SystemLog extends CommonPo {
    private String userId;
    private String operation;
    private Integer operationType;
    private String method;
    private String params;
    private String ip;
}

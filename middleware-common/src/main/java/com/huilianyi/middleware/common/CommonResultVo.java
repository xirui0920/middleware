package com.huilianyi.middleware.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CommonResultVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午3:10
 */
@Getter
@Setter
@ToString
public class CommonResultVo {
    private boolean success = false;
    private Integer code = CommonValue.FAILED_CODE;
    private String message;
}

package com.huilianyi.middleware.vo;

import com.huilianyi.middleware.common.CommonResultVo;
import lombok.Getter;
import lombok.Setter;

/**
 * ResultObjectVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午3:18
 */
@Getter
@Setter
public class ResultObjectVo<T> extends CommonResultVo {
    private T object;
}

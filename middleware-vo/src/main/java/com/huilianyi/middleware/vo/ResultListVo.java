package com.huilianyi.middleware.vo;

import com.huilianyi.middleware.common.CommonResultVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ResultListVo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午3:15
 */
@Getter
@Setter
public class ResultListVo<T> extends CommonResultVo {
    private List<T> list;
    private Long total;
}

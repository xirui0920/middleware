package com.huilianyi.middleware.common;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * CommonPo.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午2:06
 */
@Getter
@Setter
@ToString
public class CommonPo {
    /**
     * 主键
     */
    @TableId
    private String objId;
    /**
     * 是否启用
     */
    private Integer isEnable;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

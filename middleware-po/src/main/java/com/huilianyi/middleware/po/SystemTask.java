package com.huilianyi.middleware.po;

import com.huilianyi.middleware.common.CommonPo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * CsotTask.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午3:15
 */
@Getter
@Setter
@ToString
public class SystemTask extends CommonPo {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * cron表达式
     */
    private String cronValue;
    /**
     * 上次执行时间
     */
    private Date lastTime;
    /**
     * 开始执行时间
     */
    private Date startTime;
    /**
     * 下次执行时间
     */
    private Date nextTime;
}

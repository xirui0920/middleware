package com.huilianyi.middleware.util;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * CronUtil.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午2:42
 */
public class CronUtil {
    private static final Logger logger = LoggerFactory.getLogger(CronUtil.class);

    /**
     * 获取任务的下次执行时间
     *
     * @param start 开始时间
     * @param cron  cron表达式
     * @return 下次执行时间
     */
    public static Date getNextTime(Date start, String cron) {
        try {
            CronExpression cronExpression = new CronExpression(cron);
            return cronExpression.getNextValidTimeAfter(start);
        } catch (ParseException e) {
            logger.error("cron表达式解析错误，cron={}", cron);
            e.printStackTrace();
            return null;
        }
    }
}

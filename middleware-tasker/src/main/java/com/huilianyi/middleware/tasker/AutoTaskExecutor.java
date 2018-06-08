package com.huilianyi.middleware.tasker;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.huilianyi.middleware.enumeration.EIsEnable;
import com.huilianyi.middleware.po.SystemTask;
import com.huilianyi.middleware.service.SystemTaskService;
import com.huilianyi.middleware.util.CronUtil;
import com.huilianyi.middleware.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * AutoTaskExecutor.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午3:41
 */
@Component
@EnableScheduling
public class AutoTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AutoTaskExecutor.class);
    @Resource
    private SystemTaskService taskService;

    /**
     * 任务调度器执行
     */
    @Scheduled(cron = "0/30 * * * * *")
    public void start() {
        logger.info("任务调度器开始执行...");
        EntityWrapper<SystemTask> wrapper = new EntityWrapper<>();
        Date now = new Date();
        wrapper.where("1=1").eq("IS_ENABLE", EIsEnable.ENABLE.getCode())
                .le(true, "NEXT_TIME", DateUtil.offsetSecond(now, -10));
        List<SystemTask> tasks = taskService.selectAll(wrapper);
        if (null != tasks && tasks.size() > 0) {
            logger.info("共有{}条待执行任务...", tasks.size());
            Integer index = 0;
            for (SystemTask task : tasks) {
                logger.info("开始执行第{}条任务...", ++index);
                logger.info("任务【{}】开始执行，上次执行时间{}，下次执行时间{}...", task.getTaskName(),
                        DateUtil.format(task.getStartTime(), DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(CronUtil.getNextTime(task.getNextTime(), task.getCronValue()),
                                DatePattern.NORM_DATETIME_PATTERN));
                try {
                    Object obj = SpringUtil.getBean(StrUtil.lowerFirst(task.getClassName()));
                    Method method = obj.getClass().getDeclaredMethod(task.getMethodName());
                    method.setAccessible(true);
                    method.invoke(obj);
                } catch (Exception e) {
                    logger.error("任务执行失败：", e.getMessage());
                    e.printStackTrace();
                } finally {
                    task.setLastTime(task.getStartTime());
                    task.setStartTime(now);
                    task.setNextTime(CronUtil.getNextTime(now, task.getCronValue()));
                    taskService.update(task);
                }
            }
        } else {
            logger.info("暂无任务执行...");
        }
    }
}

package com.huilianyi.middleware.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonResultVo;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.po.SystemTask;
import com.huilianyi.middleware.service.SystemTaskService;
import com.huilianyi.middleware.util.CronUtil;
import com.huilianyi.middleware.util.SpringUtil;
import com.huilianyi.middleware.vo.ResultListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TaskController.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/3 上午9:56
 */
@Controller
@RequestMapping("/face/task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Resource
    private LocaleMessageSource localeMessageSource;
    @Resource
    private SystemTaskService taskService;

    /**
     * 查询任务
     *
     * @param params params
     * @return list
     */
    @RequestMapping("/select")
    @ResponseBody
    public ResultListVo<SystemTask> select(@RequestParam Map<String, Object> params) {
        ResultListVo<SystemTask> returnValue = new ResultListVo<>();

        int pageNo = null == params.get("page") ? CommonValue.ONE : Integer.valueOf(String.valueOf(params.get("page")));
        int limit = null == params.get("limit") ? CommonValue.TEN : Integer.valueOf(String.valueOf(params.get("limit")));

        EntityWrapper<SystemTask> wrapper = new EntityWrapper<>();
        if (params.size() > CommonValue.TWO) {
            wrapper.setEntity(new SystemTask());
            wrapper.where(CommonValue.SQL_WHERE);
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.TASK_SEARCH_COLUMN_TASK_NAME)))) {
                wrapper.like(CommonValue.TASK_DB_COLUMN_TASK_NAME, String.valueOf(params.get(CommonValue.TASK_SEARCH_COLUMN_TASK_NAME)));
            }
        }
        wrapper.orderBy(CommonValue.TASK_DEFAULT_ORDER_COLUMN);

        Page<SystemTask> page = new Page<>(pageNo, limit);
        page = taskService.select(page, wrapper);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        returnValue.setList(page.getRecords());
        returnValue.setTotal(page.getTotal());
        return returnValue;
    }

    /**
     * 新增
     *
     * @param task task
     * @return result
     */
    @RequestMapping("/add")
    @ResponseBody
    public CommonResultVo add(@RequestBody SystemTask task) {
        CommonResultVo returnValue = new CommonResultVo();
        if (StrUtil.isEmpty(task.getObjId())) {
            List<SystemTask> taskList = taskService.selectByClassAndMethod(task.getClassName(), task.getMethodName());
            if (null != taskList && !taskList.isEmpty()) {
                returnValue.setMessage(localeMessageSource.getMessage("task.add.class.and.method.existed"));
            } else {
                Integer result = taskService.add(task);
                if (CommonValue.ZERO.equals(result)) {
                    returnValue.setMessage(localeMessageSource.getMessage("task.add.failed"));
                } else {
                    returnValue.setSuccess(true);
                    returnValue.setCode(CommonValue.SUCCESS_CODE);
                }
            }
        } else {
            returnValue = update(task);
        }
        return returnValue;
    }

    /**
     * 修改
     *
     * @param task task
     * @return result
     */
    @RequestMapping("/update")
    @ResponseBody
    public CommonResultVo update(@RequestBody SystemTask task) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemTask taskInfo = taskService.selectByObjId(task.getObjId());
        if (null == taskInfo) {
            returnValue.setMessage(localeMessageSource.getMessage("task.update.not.existed"));
        } else {
            Integer result = taskService.update(task);
            if (CommonValue.ZERO.equals(result)) {
                returnValue.setMessage(localeMessageSource.getMessage("task.update.failed"));
            } else {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            }
        }
        return returnValue;
    }

    /**
     * 删除
     *
     * @param taskId taskId
     * @return result
     */
    @RequestMapping("/delete")
    @ResponseBody
    public CommonResultVo delete(@RequestBody String taskId) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemTask task = taskService.selectByObjId(taskId);
        if (null == task) {
            returnValue.setMessage(localeMessageSource.getMessage("task.delete.not.existed"));
        } else {
            Integer result = taskService.delete(taskId);
            if (CommonValue.ZERO.equals(result)) {
                returnValue.setMessage(localeMessageSource.getMessage("task.delete.failed"));
            } else {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            }
        }
        return returnValue;
    }

    /**
     * 删除
     *
     * @param taskIds taskIds
     * @return result
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public CommonResultVo deleteBatch(@RequestBody List<String> taskIds) {
        CommonResultVo returnValue = new CommonResultVo();
        Integer result = taskService.deleteBatch(taskIds);
        if (CommonValue.ZERO.equals(result)) {
            returnValue.setMessage(localeMessageSource.getMessage("task.delete.failed"));
        } else {
            returnValue.setSuccess(true);
            returnValue.setCode(CommonValue.SUCCESS_CODE);
            returnValue.setMessage(localeMessageSource.getMessage("task.delete.success"));
        }
        return returnValue;
    }

    /**
     * 执行
     *
     * @param taskId taskId
     * @return result
     */
    @RequestMapping("/execute")
    @ResponseBody
    public CommonResultVo execute(@RequestBody String taskId) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemTask task = taskService.selectByObjId(taskId);
        if (null == task) {
            returnValue.setMessage(localeMessageSource.getMessage("task.execute.not.existed"));
        } else {
            try {
                logger.info("任务【{}】开始执行，上次执行时间{}，下次执行时间{}...", task.getTaskName(),
                        DateUtil.format(task.getStartTime(), DatePattern.NORM_DATETIME_PATTERN),
                        DateUtil.format(CronUtil.getNextTime(task.getNextTime(), task.getCronValue()),
                                DatePattern.NORM_DATETIME_PATTERN));
                Object obj = SpringUtil.getBean(StrUtil.lowerFirst(task.getClassName()));
                Method method = obj.getClass().getDeclaredMethod(task.getMethodName());
                method.setAccessible(true);
                method.invoke(obj);
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                returnValue.setMessage(localeMessageSource.getMessage("task.execute.success"));
            } catch (Exception e) {
                logger.error("执行任务[{}]异常={}", task, e.getMessage());
                returnValue.setMessage(localeMessageSource.getMessage("task.execute.failed"));
            } finally {
                Date now = new Date();
                task.setLastTime(task.getStartTime());
                task.setStartTime(now);
                task.setNextTime(CronUtil.getNextTime(now, task.getCronValue()));
                taskService.update(task);
            }
        }
        return returnValue;
    }
}

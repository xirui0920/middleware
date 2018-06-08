package com.huilianyi.middleware.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.po.SystemTask;

import java.util.List;

/**
 * SystemTaskService.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午2:25
 */
public interface SystemTaskService {
    /**
     * 查询左右任务
     *
     * @param wrapper 查询条件
     * @return list
     */
    List<SystemTask> selectAll(EntityWrapper<SystemTask> wrapper);

    /**
     * 查询任务列表
     *
     * @param page    page
     * @param wrapper 查询条件
     * @return list
     */
    Page<SystemTask> select(Page<SystemTask> page, EntityWrapper<SystemTask> wrapper);

    /**
     * 获取
     *
     * @param objId objId
     * @return task
     */
    SystemTask selectByObjId(String objId);

    /**
     * 获取
     *
     * @param className  className
     * @param methodName methodName
     * @return task
     */
    List<SystemTask> selectByClassAndMethod(String className, String methodName);

    /**
     * 新增
     *
     * @param task 任务对象
     * @return 条数
     */
    Integer add(SystemTask task);

    /**
     * 修改
     *
     * @param task 任务对象
     * @return 条数
     */
    Integer update(SystemTask task);

    /**
     * 删除
     *
     * @param taskId taskId
     * @return 条数
     */
    Integer delete(String taskId);

    /**
     * 批量删除
     *
     * @param taskIds taskIds
     * @return 条数
     */
    Integer deleteBatch(List<String> taskIds);
}

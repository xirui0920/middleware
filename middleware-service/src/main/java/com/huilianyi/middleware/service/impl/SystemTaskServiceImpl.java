package com.huilianyi.middleware.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.dao.SystemTaskMapper;
import com.huilianyi.middleware.po.SystemTask;
import com.huilianyi.middleware.service.SystemTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskService.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午3:22
 */
@Service
public class SystemTaskServiceImpl implements SystemTaskService {
    @Resource
    private SystemTaskMapper taskMapper;

    /**
     * 查询所有任务
     *
     * @param wrapper 查询条件
     * @return list
     */
    @Override
    public List<SystemTask> selectAll(EntityWrapper<SystemTask> wrapper) {
        return taskMapper.selectList(wrapper);
    }

    /**
     * 查询任务列表
     *
     * @param page    page
     * @param wrapper 查询条件
     * @return list
     */
    @Override
    public Page<SystemTask> select(Page<SystemTask> page, EntityWrapper<SystemTask> wrapper) {
        page.setTotal(taskMapper.selectCount(null));
        return page.setRecords(taskMapper.selectPage(page, wrapper));
    }

    /**
     * 获取
     *
     * @param objId objId
     * @return task
     */
    @Override
    public SystemTask selectByObjId(String objId) {
        return taskMapper.selectById(objId);
    }

    /**
     * 获取
     *
     * @param className  className
     * @param methodName methodName
     * @return task
     */
    @Override
    public List<SystemTask> selectByClassAndMethod(String className, String methodName) {
        Map<String, Object> map = new HashMap<>(CommonValue.TWO);
        map.put("CLASS_NAME", className);
        map.put("METHOD_NAME", methodName);
        return taskMapper.selectByMap(map);
    }

    /**
     * 新增
     *
     * @param task 任务对象
     * @return 条数
     */
    @Override
    public Integer add(SystemTask task) {
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());
        return taskMapper.insert(task);
    }

    /**
     * 更新任务
     *
     * @param task 任务对象
     * @return 更新条数
     */
    @Override
    public Integer update(SystemTask task) {
        task.setUpdateTime(new Date());
        return taskMapper.updateById(task);
    }

    /**
     * 删除
     *
     * @param taskId taskId
     * @return 条数
     */
    @Override
    public Integer delete(String taskId) {
        return taskMapper.deleteById(taskId);
    }

    /**
     * 批量删除
     *
     * @param taskIds taskIds
     * @return 条数
     */
    @Override
    public Integer deleteBatch(List<String> taskIds) {
        return taskMapper.deleteBatchIds(taskIds);
    }
}

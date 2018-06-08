package com.huilianyi.middleware.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.po.SystemUser;
import com.huilianyi.middleware.po.SystemUserRole;

import java.util.List;

/**
 * SystemTaskService.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午2:25
 */
public interface SystemUserService {
    /**
     * 根据主键查找用户
     *
     * @param objId 主键
     * @return 用户信息
     */
    SystemUser selectByObjId(String objId);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return list
     */
    List<SystemUser> selectByUsername(String username);

    /**
     * 查找用户
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    Page<SystemUser> select(Page<SystemUser> page, EntityWrapper<SystemUser> wrapper);

    /**
     * 查找用户角色
     *
     * @param userId userId
     * @return userRelRole
     */
    SystemUserRole selectUserRole(String userId);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 数量
     */
    Integer update(SystemUser user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 数量
     */
    Integer updatePassword(SystemUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 数量
     */
    Integer save(SystemUser user);

    /**
     * 删除用户
     *
     * @param objId objId
     * @return 数量
     */
    Integer delete(String objId);

    /**
     * 批量删除用户
     *
     * @param objIds objIds
     * @return 数量
     */
    Integer deleteBatch(List<String> objIds);
}

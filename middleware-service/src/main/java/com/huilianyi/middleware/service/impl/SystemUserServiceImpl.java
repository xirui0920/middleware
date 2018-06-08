package com.huilianyi.middleware.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.dao.SystemUserMapper;
import com.huilianyi.middleware.dao.SystemUserRoleMapper;
import com.huilianyi.middleware.po.SystemUser;
import com.huilianyi.middleware.po.SystemUserRole;
import com.huilianyi.middleware.service.SystemUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * SystemUserServiceImpl.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午4:32
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper userMapper;
    @Resource
    private SystemUserRoleMapper userRoleMapper;

    /**
     * 根据主键查找用户
     *
     * @param objId 主键
     * @return 用户信息
     */
    @Override
    public SystemUser selectByObjId(String objId) {
        return userMapper.selectById(objId);
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return list
     */
    @Override
    public List<SystemUser> selectByUsername(String username) {
        EntityWrapper<SystemUser> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq(CommonValue.USER_DB_COLUMN_USERNAME, username);
        return userMapper.selectList(wrapper);
    }

    /**
     * 查找用户
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    @Override
    public Page<SystemUser> select(Page<SystemUser> page, EntityWrapper<SystemUser> wrapper) {
        page.setTotal(userMapper.selectCount(null));
        return page.setRecords(userMapper.selectPage(page, wrapper));
    }

    /**
     * 查找用户角色
     *
     * @param userId userId
     * @return userRelRole
     */
    @Override
    public SystemUserRole selectUserRole(String userId) {
        SystemUserRole userRole = new SystemUserRole();
        userRole.setUserId(userId);
        return userRoleMapper.selectOne(userRole);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 数量
     */
    @Override
    public Integer update(SystemUser user) {
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    /**
     * 更新用户密码
     *
     * @param user 用户信息
     * @return 数量
     */
    @Override
    public Integer updatePassword(SystemUser user) {
        user.setPassword(SecureUtil.sha1(user.getUsername() + CommonValue.SYSTEM_COLON + user.getPassword()));
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 数量
     */
    @Override
    public Integer save(SystemUser user) {
        user.setPassword(SecureUtil.sha1(user.getUsername() + CommonValue.SYSTEM_COLON + CommonValue.SYSTEM_DEFAULT_PASSWORD));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insert(user);
    }

    /**
     * 删除用户
     *
     * @param objId objId
     * @return 数量
     */
    @Override
    public Integer delete(String objId) {
        EntityWrapper<SystemUserRole> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("USER_ID", objId);
        userRoleMapper.delete(wrapper);
        return userMapper.deleteById(objId);
    }

    /**
     * 批量删除用户
     *
     * @param objIds objIds
     * @return 数量
     */
    @Override
    public Integer deleteBatch(List<String> objIds) {
        EntityWrapper<SystemUserRole> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).in("USER_ID", objIds);
        userRoleMapper.delete(wrapper);
        return userMapper.deleteBatchIds(objIds);
    }
}

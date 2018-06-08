package com.huilianyi.middleware.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.po.SystemRole;
import com.huilianyi.middleware.po.SystemUserRole;

import java.util.List;

/**
 * SystemRoleService.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:48
 */
public interface SystemRoleService {
    /**
     * 根据主键查询角色
     *
     * @param objId objId
     * @return role
     */
    SystemRole selectByObjId(String objId);

    /**
     * 查找角色
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    Page<SystemRole> select(Page<SystemRole> page, EntityWrapper<SystemRole> wrapper);

    /**
     * 查找角色
     *
     * @param roleName roleName
     * @return list
     */
    List<SystemRole> selectByRoleName(String roleName);

    /**
     * 查找所有已启用角色
     *
     * @return list
     */
    List<SystemRole> selectAllEnableRoles();

    /**
     * 绑定用户角色
     *
     * @param userRelRole userRelRole
     * @return boolean
     */
    boolean userRelRole(SystemUserRole userRelRole);

    /**
     * 新增角色信息
     *
     * @param role role
     * @return 数量
     */
    Integer save(SystemRole role);

    /**
     * 更新角色信息
     *
     * @param role role
     * @return 数量
     */
    Integer update(SystemRole role);

    /**
     * 删除角色
     *
     * @param objId objId
     * @return 数量
     */
    Integer delete(String objId);

    /**
     * 批量删除角色
     *
     * @param objIds objIds
     * @return 数量
     */
    Integer deleteBatch(List<String> objIds);

    /**
     * 判断角色是否未被引用
     *
     * @param objId objId
     * @return boolean
     */
    boolean isRoleNotUsed(String objId);
}

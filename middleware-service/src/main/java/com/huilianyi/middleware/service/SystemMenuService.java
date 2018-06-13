package com.huilianyi.middleware.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.po.SystemMenu;
import com.huilianyi.middleware.vo.MenuTreeVo;
import com.huilianyi.middleware.vo.RoleRelMenuVo;
import com.huilianyi.middleware.vo.UserMenuVo;

import java.util.List;

/**
 * SystemMenuService.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:48
 */
public interface SystemMenuService {
    /**
     * 根据主键查询菜单
     *
     * @param objId objId
     * @return menu
     */
    SystemMenu selectByObjId(String objId);

    /**
     * 查找菜单
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    Page<SystemMenu> select(Page<SystemMenu> page, EntityWrapper<SystemMenu> wrapper);

    /**
     * 查找菜单
     *
     * @param menuKey menuKey
     * @return list
     */
    List<SystemMenu> selectByMenuKey(String menuKey);

    /**
     * 查找菜单
     *
     * @param menuUri menuUri
     * @return list
     */
    List<SystemMenu> selectByMenuUri(String menuUri);

    /**
     * 查询子菜单的数量
     *
     * @param parentId parentId
     * @return count
     */
    Integer selectSonMenuCount(String parentId);

    /**
     * 新增菜单信息
     *
     * @param menu menu
     * @return 数量
     */
    Integer save(SystemMenu menu);

    /**
     * 更新菜单信息
     *
     * @param menu menu
     * @return 数量
     */
    Integer update(SystemMenu menu);

    /**
     * 删除菜单
     *
     * @param objId objId
     * @return 数量
     */
    Integer delete(String objId);

    /**
     * 批量删除菜单
     *
     * @param objIds objIds
     * @return 数量
     */
    Integer deleteBatch(List<String> objIds);

    /**
     * 获取角色关联的菜单
     *
     * @param roleId roleId
     * @return list
     */
    List<MenuTreeVo> selectMenusByRole(String roleId);

    /**
     * 获取角色关联的已启用菜单
     *
     * @param roleId roleId
     * @return list
     */
    List<MenuTreeVo> selectEnableMenusByRole(String roleId);

    /**
     * 获取所有已启用路径菜单
     *
     * @return list
     */
    List<SystemMenu> selectPathMenus();

    /**
     * 获取用户关联的已启用菜单
     *
     * @param userId userId
     * @return list
     */
    List<UserMenuVo> selectUserMenu(String userId);

    /**
     * 绑定角色菜单
     *
     * @param roleRelMenuVo roleRelMenuVo
     * @return boolean
     */
    boolean roleRelMenu(RoleRelMenuVo roleRelMenuVo);
}

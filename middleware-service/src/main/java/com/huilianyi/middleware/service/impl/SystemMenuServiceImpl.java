package com.huilianyi.middleware.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.dao.SystemMenuMapper;
import com.huilianyi.middleware.dao.SystemRoleMenuMapper;
import com.huilianyi.middleware.enumeration.EMenuType;
import com.huilianyi.middleware.po.SystemMenu;
import com.huilianyi.middleware.po.SystemRoleMenu;
import com.huilianyi.middleware.service.SystemMenuService;
import com.huilianyi.middleware.vo.MenuTreeVo;
import com.huilianyi.middleware.vo.RoleRelMenuVo;
import com.huilianyi.middleware.vo.UserMenuVo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SystemMenuServiceImpl.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:48
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Resource
    private SystemRoleMenuMapper roleMenuMapper;
    @Resource
    private SystemMenuMapper menuMapper;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private LocaleMessageSource localeMessageSource;

    /**
     * 根据主键查询菜单
     *
     * @param objId objId
     * @return menu
     */
    @Override
    public SystemMenu selectByObjId(String objId) {
        return menuMapper.selectById(objId);
    }

    /**
     * 查找菜单
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    @Override
    public Page<SystemMenu> select(Page<SystemMenu> page, EntityWrapper<SystemMenu> wrapper) {
        page.setTotal(menuMapper.selectCount(null));
        return page.setRecords(menuMapper.selectPage(page, wrapper));
    }

    /**
     * 查找菜单
     *
     * @param menuKey menuKey
     * @return list
     */
    @Override
    public List<SystemMenu> selectByMenuKey(String menuKey) {
        EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq(CommonValue.MENU_DB_COLUMN_MENU_KEY, menuKey);
        return menuMapper.selectList(wrapper);
    }

    /**
     * 查找菜单
     *
     * @param menuUri menuUri
     * @return list
     */
    @Override
    public List<SystemMenu> selectByMenuUri(String menuUri) {
        EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq(CommonValue.MENU_DB_COLUMN_MENU_URI, menuUri);
        return menuMapper.selectList(wrapper);
    }

    /**
     * 查询子菜单的数量
     *
     * @param parentId parentId
     * @return count
     */
    @Override
    public Integer selectSonMenuCount(String parentId) {
        EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("PARENT_ID", parentId).eq("IS_ENABLE", CommonValue.ONE);
        return menuMapper.selectCount(wrapper);
    }

    /**
     * 新增菜单信息
     *
     * @param menu menu
     * @return 数量
     */
    @Override
    public Integer save(SystemMenu menu) {
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return menuMapper.insert(menu);
    }

    /**
     * 更新菜单信息
     *
     * @param menu menu
     * @return 数量
     */
    @Override
    public Integer update(SystemMenu menu) {
        menu.setUpdateTime(new Date());
        return menuMapper.updateById(menu);
    }

    /**
     * 删除菜单
     *
     * @param objId objId
     * @return 数量
     */
    @Override
    public Integer delete(String objId) {
        EntityWrapper<SystemRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("MENU_ID", objId);
        roleMenuMapper.delete(wrapper);
        return menuMapper.deleteById(objId);
    }

    /**
     * 批量删除菜单
     *
     * @param objIds objIds
     * @return 数量
     */
    @Override
    public Integer deleteBatch(List<String> objIds) {
        EntityWrapper<SystemRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).in("MENU_ID", objIds);
        roleMenuMapper.delete(wrapper);
        return menuMapper.deleteBatchIds(objIds);
    }

    /**
     * 获取角色关联的菜单
     *
     * @param roleId roleId
     * @return list
     */
    @Override
    public List<MenuTreeVo> selectMenusByRole(String roleId) {
        List<MenuTreeVo> result = new ArrayList<>();
        List<String> menuIds = roleMenuMapper.selectMenuByRoleId(roleId);
        EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("IS_ENABLE", CommonValue.ONE);
        wrapper.orderBy("SORT", true);
        List<SystemMenu> menuList = menuMapper.selectList(wrapper);
        MenuTreeVo menuTreeVo;
        for (SystemMenu menu : menuList) {
            menuTreeVo = new MenuTreeVo();
            menuTreeVo.setId(menu.getObjId());
            menuTreeVo.setName(menu.getMenuKey());
            menuTreeVo.setPId(menu.getParentId());
            menuTreeVo.setIconSkin(menu.getMenuIcon());
            menuTreeVo.setNameWithIcon(menu.getMenuKey());
            menuTreeVo.setOpen(true);
            if (menuIds.contains(menu.getObjId())) {
                menuTreeVo.setChecked(true);
            }
            result.add(menuTreeVo);
        }
        return result;
    }

    /**
     * 获取角色关联的已启用菜单
     *
     * @param roleId roleId
     * @return list
     */
    @Override
    public List<MenuTreeVo> selectEnableMenusByRole(String roleId) {
        List<MenuTreeVo> result = new ArrayList<>();
        List<String> menuIds = roleMenuMapper.selectMenuByRoleId(roleId);
        if (!menuIds.isEmpty()) {
            EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
            wrapper.where(CommonValue.SQL_WHERE).eq("IS_ENABLE", CommonValue.ONE).in("OBJ_ID", menuIds);
            wrapper.orderBy("SORT", true);
            List<SystemMenu> menuList = menuMapper.selectList(wrapper);
            MenuTreeVo menuTreeVo;
            for (SystemMenu menu : menuList) {
                menuTreeVo = new MenuTreeVo();
                menuTreeVo.setId(menu.getObjId());
                menuTreeVo.setName(menu.getMenuKey());
                menuTreeVo.setPId(menu.getParentId());
                menuTreeVo.setIconSkin(menu.getMenuIcon());
                menuTreeVo.setNameWithIcon(menu.getMenuKey());
                menuTreeVo.setOpen(true);
                result.add(menuTreeVo);
            }
        }
        return result;
    }

    /**
     * 获取用户关联的已启用菜单
     *
     * @param userId userId
     * @return list
     */
    @Override
    public List<UserMenuVo> selectUserMenu(String userId) {
        List<UserMenuVo> list = menuMapper.selectUserParentMenu(userId);
        if (!list.isEmpty()) {
            for (UserMenuVo userMenuVo : list) {
                userMenuVo.setMenuKey(localeMessageSource.getMessage(userMenuVo.getMenuKey()));
                selectSonMenu(userId, userMenuVo);
            }
        }
        return list;
    }

    /**
     * 获取父菜单的子菜单
     *
     * @param userMenuVo userMenuVo
     */
    private void selectSonMenu(String userId, UserMenuVo userMenuVo) {
        if (EMenuType.PATH.getCode().equals(userMenuVo.getMenuType())) {
            List<UserMenuVo> sonMenus = menuMapper.selectSonMenu(userId, userMenuVo.getObjId());
            userMenuVo.setList(sonMenus);
            if (!sonMenus.isEmpty()) {
                for (UserMenuVo sonMenu : sonMenus) {
                    sonMenu.setMenuKey(localeMessageSource.getMessage(sonMenu.getMenuKey()));
                    selectSonMenu(userId, sonMenu);
                }
            }
        }
    }

    /**
     * 绑定角色菜单
     *
     * @param roleRelMenuVo roleRelMenuVo
     * @return boolean
     */
    @Override
    public boolean roleRelMenu(RoleRelMenuVo roleRelMenuVo) {
        boolean flag = true;
        String roleId = roleRelMenuVo.getRoleId();
        List<String> menuIds = roleRelMenuVo.getMenuIds();
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        roleMenuMapper = sqlSession.getMapper(SystemRoleMenuMapper.class);
        try {
            EntityWrapper<SystemRoleMenu> wrapper = new EntityWrapper<>();
            wrapper.where(CommonValue.SQL_WHERE).eq("ROLE_ID", roleId);
            roleMenuMapper.delete(wrapper);

            SystemRoleMenu systemRoleMenu;
            for (String menuId : menuIds) {
                systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setObjId(RandomUtil.randomUUID());
                systemRoleMenu.setRoleId(roleRelMenuVo.getRoleId());
                systemRoleMenu.setMenuId(menuId);
                roleMenuMapper.insert(systemRoleMenu);
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            flag = false;
        }
        return flag;
    }
}

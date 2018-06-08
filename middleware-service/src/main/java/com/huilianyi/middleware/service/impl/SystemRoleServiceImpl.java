package com.huilianyi.middleware.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.dao.SystemRoleMapper;
import com.huilianyi.middleware.dao.SystemRoleMenuMapper;
import com.huilianyi.middleware.dao.SystemUserRoleMapper;
import com.huilianyi.middleware.po.SystemRole;
import com.huilianyi.middleware.po.SystemRoleMenu;
import com.huilianyi.middleware.po.SystemUserRole;
import com.huilianyi.middleware.service.SystemRoleService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * SystemRoleServiceImpl.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:48
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Resource
    private SystemRoleMapper roleMapper;
    @Resource
    private SystemUserRoleMapper userRoleMapper;
    @Resource
    private SystemRoleMenuMapper roleMenuMapper;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据主键查询角色
     *
     * @param objId objId
     * @return role
     */
    @Override
    public SystemRole selectByObjId(String objId) {
        return roleMapper.selectById(objId);
    }

    /**
     * 查找角色
     *
     * @param page    page
     * @param wrapper wrapper
     * @return list
     */
    @Override
    public Page<SystemRole> select(Page<SystemRole> page, EntityWrapper<SystemRole> wrapper) {
        page.setTotal(roleMapper.selectCount(null));
        return page.setRecords(roleMapper.selectPage(page, wrapper));
    }

    /**
     * 查找角色
     *
     * @param roleName roleName
     * @return list
     */
    @Override
    public List<SystemRole> selectByRoleName(String roleName) {
        EntityWrapper<SystemRole> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq(CommonValue.ROLE_DB_COLUMN_ROLE_NAME, roleName);
        wrapper.orderBy("ROLE_NAME", true);
        return roleMapper.selectList(wrapper);
    }

    /**
     * 查找所有已启用角色
     *
     * @return list
     */
    @Override
    public List<SystemRole> selectAllEnableRoles() {
        EntityWrapper<SystemRole> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("IS_ENABLE", CommonValue.ONE);
        wrapper.orderBy("ROLE_NAME", true);
        return roleMapper.selectList(wrapper);
    }

    /**
     * 绑定用户角色
     *
     * @param userRelRole userRelRole
     * @return boolean
     */
    @Override
    public boolean userRelRole(SystemUserRole userRelRole) {
        boolean flag = true;
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        userRoleMapper = sqlSession.getMapper(SystemUserRoleMapper.class);
        try {
            EntityWrapper<SystemUserRole> wrapper = new EntityWrapper<>();
            wrapper.where(CommonValue.SQL_WHERE).eq("USER_ID", userRelRole.getUserId());
            userRoleMapper.delete(wrapper);
            if (StrUtil.isNotEmpty(userRelRole.getRoleId())) {
                userRelRole.setObjId(RandomUtil.randomUUID());
                userRoleMapper.insert(userRelRole);
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            flag = false;
        }
        return flag;
    }

    /**
     * 新增角色信息
     *
     * @param role role
     * @return 数量
     */
    @Override
    public Integer save(SystemRole role) {
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        return roleMapper.insert(role);
    }

    /**
     * 更新角色信息
     *
     * @param role role
     * @return 数量
     */
    @Override
    public Integer update(SystemRole role) {
        role.setUpdateTime(new Date());
        return roleMapper.updateById(role);
    }

    /**
     * 删除角色
     *
     * @param objId objId
     * @return 数量
     */
    @Override
    public Integer delete(String objId) {
        EntityWrapper<SystemRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("ROLE_ID", objId);
        roleMenuMapper.delete(wrapper);
        return roleMapper.deleteById(objId);
    }

    /**
     * 批量删除角色
     *
     * @param objIds objIds
     * @return 数量
     */
    @Override
    public Integer deleteBatch(List<String> objIds) {
        EntityWrapper<SystemRoleMenu> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).in("ROLE_ID", objIds);
        roleMenuMapper.delete(wrapper);
        return roleMapper.deleteBatchIds(objIds);
    }

    /**
     * 判断角色是否未被引用
     *
     * @param objId objId
     * @return boolean
     */
    @Override
    public boolean isRoleNotUsed(String objId) {
        EntityWrapper<SystemUserRole> wrapper = new EntityWrapper<>();
        wrapper.where(CommonValue.SQL_WHERE).eq("ROLE_ID", objId);
        Integer count = userRoleMapper.selectCount(wrapper);
        return CommonValue.ZERO.equals(count);
    }
}

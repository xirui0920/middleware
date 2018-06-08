package com.huilianyi.middleware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huilianyi.middleware.po.SystemRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SystemRoleMenuMapper.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:14
 */
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenu> {
    /**
     * 查询角色关联的菜单id
     *
     * @param roleId roleId
     * @return list
     */
    List<String> selectMenuByRoleId(@Param("roleId") String roleId);
}

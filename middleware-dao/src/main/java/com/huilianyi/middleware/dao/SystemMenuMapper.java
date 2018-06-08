package com.huilianyi.middleware.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huilianyi.middleware.po.SystemMenu;
import com.huilianyi.middleware.vo.UserMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SystemMenuMapper.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:14
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
    /**
     * 获取用户关联的已启用菜单
     *
     * @param userId userId
     * @return list
     */
    List<UserMenuVo> selectUserParentMenu(@Param("userId") String userId);

    /**
     * 获取子菜单
     *
     * @param userId   userId
     * @param parentId parentId
     * @return list
     */
    List<UserMenuVo> selectSonMenu(@Param("userId") String userId, @Param("parentId") String parentId);
}

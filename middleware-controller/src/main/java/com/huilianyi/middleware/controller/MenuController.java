package com.huilianyi.middleware.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonResultVo;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.enumeration.EMenuType;
import com.huilianyi.middleware.po.SystemMenu;
import com.huilianyi.middleware.service.SystemMenuService;
import com.huilianyi.middleware.vo.MenuTreeVo;
import com.huilianyi.middleware.vo.ResultListVo;
import com.huilianyi.middleware.vo.RoleRelMenuVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * MenuController.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:57
 */
@Controller
@RequestMapping("/face/menu")
public class MenuController {

    @Resource
    private LocaleMessageSource localeMessageSource;
    @Resource
    private SystemMenuService menuService;

    /**
     * 查询菜单
     *
     * @param params params
     * @return list
     */
    @RequestMapping("/select")
    @ResponseBody
    public ResultListVo<SystemMenu> select(@RequestParam Map<String, Object> params) {
        ResultListVo<SystemMenu> returnValue = new ResultListVo<>();

        int pageNo = null == params.get("page") ? CommonValue.ONE : Integer.valueOf(String.valueOf(params.get("page")));
        int limit = null == params.get("limit") ? CommonValue.TEN : Integer.valueOf(String.valueOf(params.get("limit")));

        EntityWrapper<SystemMenu> wrapper = new EntityWrapper<>();
        if (params.size() > CommonValue.TWO) {
            wrapper.setEntity(new SystemMenu());
            wrapper.where(CommonValue.SQL_WHERE);
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.MENU_SEARCH_COLUMN_MENU_KEY)))) {
                wrapper.like(CommonValue.MENU_DB_COLUMN_MENU_KEY, String.valueOf(params.get(CommonValue.MENU_SEARCH_COLUMN_MENU_KEY)));
            }
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.MENU_SEARCH_COLUMN_MENU_TYPE)))) {
                wrapper.eq(CommonValue.MENU_DB_COLUMN_MENU_TYPE, params.get(CommonValue.MENU_SEARCH_COLUMN_MENU_TYPE));
            }
        }
        wrapper.orderBy(CommonValue.MENU_DEFAULT_ORDER_COLUMN);

        Page<SystemMenu> page = new Page<>(pageNo, limit);
        page = menuService.select(page, wrapper);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        returnValue.setList(page.getRecords());
        returnValue.setTotal(page.getTotal());
        return returnValue;
    }

    /**
     * 获取角色关联的菜单
     *
     * @param roleId roleId
     * @return list
     */
    @RequestMapping("/getRoleMenu")
    @ResponseBody
    public ResultListVo<MenuTreeVo> selectMenusByRoleId(@RequestBody String roleId) {
        ResultListVo<MenuTreeVo> returnValue = new ResultListVo<>();
        returnValue.setSuccess(true);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setList(menuService.selectMenusByRole(roleId));
        return returnValue;
    }

    /**
     * 获取角色关联的已启用菜单
     *
     * @param roleId roleId
     * @return list
     */
    @RequestMapping("/getEnableRoleMenu")
    @ResponseBody
    public ResultListVo<MenuTreeVo> selectEnableMenusByRoleId(@RequestBody String roleId) {
        ResultListVo<MenuTreeVo> returnValue = new ResultListVo<>();
        returnValue.setSuccess(true);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setList(menuService.selectEnableMenusByRole(roleId));
        return returnValue;
    }

    /**
     * 绑定角色菜单
     *
     * @param roleRelMenuVo roleRelMenuVo
     * @return list
     */
    @RequestMapping("/roleRelMenu")
    @ResponseBody
    public CommonResultVo roleRelMenu(@RequestBody RoleRelMenuVo roleRelMenuVo) {
        CommonResultVo returnValue = new CommonResultVo();
        boolean flag = menuService.roleRelMenu(roleRelMenuVo);
        if (flag) {
            returnValue.setSuccess(true);
            returnValue.setCode(CommonValue.SUCCESS_CODE);
        } else {
            returnValue.setMessage(localeMessageSource.getMessage("menu.rel.role.failed"));
        }
        return returnValue;
    }

    /**
     * 新增菜单信息
     *
     * @param menu menu
     * @return result
     */
    @RequestMapping("/add")
    @ResponseBody
    public CommonResultVo add(@RequestBody SystemMenu menu) {
        CommonResultVo returnValue = new CommonResultVo();
        if (StrUtil.isEmpty(menu.getObjId())) {
            List<SystemMenu> list = menuService.selectByMenuKey(menu.getMenuKey());
            if (null != list && !list.isEmpty()) {
                returnValue.setMessage(localeMessageSource.getMessage("menu.add.menuKey.existed"));
            }

            list = menuService.selectByMenuUri(menu.getMenuUri());
            if (null != list && !list.isEmpty() && menu.getMenuType().equals(EMenuType.LEAF.getCode())) {
                returnValue.setMessage(localeMessageSource.getMessage("menu.add.menuUri.existed"));
            } else {
                Integer result = menuService.save(menu);
                if (CommonValue.ONE.equals(result)) {
                    returnValue.setSuccess(true);
                    returnValue.setCode(CommonValue.SUCCESS_CODE);
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("menu.add.failed"));
                }
            }
        } else {
            returnValue = update(menu);
        }
        return returnValue;
    }

    /**
     * 修改菜单信息
     *
     * @param menu menu
     * @return result
     */
    @RequestMapping("/update")
    @ResponseBody
    public CommonResultVo update(@RequestBody SystemMenu menu) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemMenu menuInfo = menuService.selectByObjId(menu.getObjId());
        if (null == menuInfo) {
            returnValue.setMessage(localeMessageSource.getMessage("menu.update.menuKey.not.existed"));
        } else {
            if (CommonValue.ZERO.equals(menuInfo.getMenuType()) && !menu.getMenuType().equals(menuInfo.getMenuType())
                    && !CommonValue.ZERO.equals(menuService.selectSonMenuCount(menu.getObjId()))) {
                returnValue.setMessage(localeMessageSource.getMessage("menu.update.has.son.menu"));
                return returnValue;
            }
            if (!menu.getIsEnable().equals(menuInfo.getIsEnable())
                    && !CommonValue.ZERO.equals(menuService.selectSonMenuCount(menu.getObjId()))) {
                returnValue.setMessage(localeMessageSource.getMessage("menu.update.has.son.menu"));
                return returnValue;
            }
            menu.setCreateBy(menuInfo.getCreateBy());
            Integer result = menuService.update(menu);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("menu.update.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 删除菜单信息
     *
     * @param objId objId
     * @return result
     */
    @RequestMapping("/delete")
    @ResponseBody
    public CommonResultVo delete(@RequestBody String objId) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemMenu menu = menuService.selectByObjId(objId);
        if (null == menu) {
            returnValue.setMessage(localeMessageSource.getMessage("menu.delete.menuKey.not.existed"));
        } else {
            Integer result = menuService.delete(objId);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                returnValue.setMessage(localeMessageSource.getMessage("menu.delete.success"));
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("menu.delete.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 删除菜单信息
     *
     * @param objIds objIds
     * @return result
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public CommonResultVo deleteBatch(@RequestBody List<String> objIds) {
        CommonResultVo returnValue = new CommonResultVo();
        Integer result = menuService.deleteBatch(objIds);
        if (CommonValue.ZERO.equals(result)) {
            returnValue.setMessage(localeMessageSource.getMessage("menu.delete.failed"));
        } else {
            returnValue.setSuccess(true);
            returnValue.setCode(CommonValue.SUCCESS_CODE);
            returnValue.setMessage(localeMessageSource.getMessage("menu.delete.success"));
        }
        return returnValue;
    }
}

package com.huilianyi.middleware.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonResultVo;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.po.SystemRole;
import com.huilianyi.middleware.po.SystemUserRole;
import com.huilianyi.middleware.service.SystemRoleService;
import com.huilianyi.middleware.vo.ResultListVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RoleController.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/25 上午10:56
 */
@Controller
@RequestMapping("/face/role")
public class RoleController {

    @Resource
    private LocaleMessageSource localeMessageSource;
    @Resource
    private SystemRoleService roleService;

    /**
     * 查询角色
     *
     * @param params params
     * @return list
     */
    @RequestMapping("/select")
    @ResponseBody
    public ResultListVo<SystemRole> select(@RequestParam Map<String, Object> params) {
        ResultListVo<SystemRole> returnValue = new ResultListVo<>();

        int pageNo = null == params.get("page") ? CommonValue.ONE : Integer.valueOf(String.valueOf(params.get("page")));
        int limit = null == params.get("limit") ? CommonValue.TEN : Integer.valueOf(String.valueOf(params.get("limit")));

        EntityWrapper<SystemRole> wrapper = new EntityWrapper<>();
        if (params.size() > CommonValue.TWO) {
            wrapper.setEntity(new SystemRole());
            wrapper.where(CommonValue.SQL_WHERE);
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.ROLE_SEARCH_COLUMN_ROLE_NAME)))) {
                wrapper.like(CommonValue.ROLE_DB_COLUMN_ROLE_NAME, String.valueOf(params.get(CommonValue.ROLE_SEARCH_COLUMN_ROLE_NAME)));
            }
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.ROLE_SEARCH_COLUMN_ROLE_NICKNAME)))) {
                wrapper.like(CommonValue.ROLE_DB_COLUMN_ROLE_NICKNAME, String.valueOf(params.get(CommonValue.ROLE_SEARCH_COLUMN_ROLE_NICKNAME)));
            }
        }
        wrapper.orderBy(CommonValue.ROLE_DEFAULT_ORDER_COLUMN);

        Page<SystemRole> page = new Page<>(pageNo, limit);
        page = roleService.select(page, wrapper);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        returnValue.setList(page.getRecords());
        returnValue.setTotal(page.getTotal());
        return returnValue;
    }

    /**
     * 绑定用户角色
     *
     * @param userRelRole userRelRole
     * @return result
     */
    @RequestMapping("/userRelRole")
    @ResponseBody
    public CommonResultVo userRelRole(@RequestBody SystemUserRole userRelRole) {
        CommonResultVo returnValue = new CommonResultVo();
        boolean flag = roleService.userRelRole(userRelRole);
        if (flag) {
            returnValue.setSuccess(true);
            returnValue.setCode(CommonValue.SUCCESS_CODE);
        } else {
            returnValue.setMessage(localeMessageSource.getMessage("menu.rel.role.failed"));
        }
        return returnValue;
    }

    /**
     * 新增角色信息
     *
     * @param role role
     * @return result
     */
    @RequestMapping("/add")
    @ResponseBody
    public CommonResultVo add(@RequestBody SystemRole role) {
        CommonResultVo returnValue = new CommonResultVo();
        if (StrUtil.isEmpty(role.getObjId())) {
            List<SystemRole> list = roleService.selectByRoleName(role.getRoleName());
            if (null != list && !list.isEmpty()) {
                returnValue.setMessage(localeMessageSource.getMessage("role.add.roleName.existed"));
            } else {
                Integer result = roleService.save(role);
                if (CommonValue.ONE.equals(result)) {
                    returnValue.setSuccess(true);
                    returnValue.setCode(CommonValue.SUCCESS_CODE);
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("role.add.failed"));
                }
            }
        } else {
            returnValue = update(role);
        }
        return returnValue;
    }

    /**
     * 修改角色信息
     *
     * @param role role
     * @return result
     */
    @RequestMapping("/update")
    @ResponseBody
    public CommonResultVo update(@RequestBody SystemRole role) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemRole roleInfo = roleService.selectByObjId(role.getObjId());
        if (null == roleInfo) {
            returnValue.setMessage(localeMessageSource.getMessage("role.update.roleName.not.existed"));
        } else {
            role.setCreateBy(roleInfo.getCreateBy());
            Integer result = roleService.update(role);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("role.update.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 删除角色信息
     *
     * @param objId objId
     * @return result
     */
    @RequestMapping("/delete")
    @ResponseBody
    public CommonResultVo delete(@RequestBody String objId) {
        CommonResultVo returnValue = new CommonResultVo();
        if (roleService.isRoleNotUsed(objId)) {
            SystemRole role = roleService.selectByObjId(objId);
            if (null == role) {
                returnValue.setMessage(localeMessageSource.getMessage("role.delete.roleName.not.existed"));
            } else {
                Integer result = roleService.delete(objId);
                if (CommonValue.ONE.equals(result)) {
                    returnValue.setSuccess(true);
                    returnValue.setCode(CommonValue.SUCCESS_CODE);
                    returnValue.setMessage(localeMessageSource.getMessage("role.delete.success"));
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("role.delete.failed"));
                }
            }
        } else {
            returnValue.setMessage(localeMessageSource.getMessage("role.delete.roleName.used"));
        }
        return returnValue;
    }

    /**
     * 删除角色信息
     *
     * @param objIds objIds
     * @return result
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public CommonResultVo deleteBatch(@RequestBody List<String> objIds) {
        boolean flag = false;
        List<String> roleIds = new ArrayList<>();
        CommonResultVo returnValue = new CommonResultVo();
        for (String objId : objIds) {
            if (roleService.isRoleNotUsed(objId)) {
                roleIds.add(objId);
            } else {
                flag = true;
            }
        }
        if (roleIds.isEmpty() && flag) {
            returnValue.setMessage(localeMessageSource.getMessage("role.delete.roleName.used"));
        } else {
            Integer result = roleService.deleteBatch(roleIds);
            if (CommonValue.ZERO.equals(result)) {
                returnValue.setMessage(localeMessageSource.getMessage("role.delete.failed"));
            } else {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                if (flag) {
                    returnValue.setMessage(localeMessageSource.getMessage("role.delete.success.without.used"));
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("role.delete.success"));
                }
            }
        }
        return returnValue;
    }
}

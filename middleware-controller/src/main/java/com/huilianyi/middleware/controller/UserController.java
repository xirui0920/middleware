package com.huilianyi.middleware.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.huilianyi.middleware.common.CommonResultVo;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.po.SystemUser;
import com.huilianyi.middleware.service.SystemUserService;
import com.huilianyi.middleware.vo.LoginVo;
import com.huilianyi.middleware.vo.ResultListVo;
import com.huilianyi.middleware.vo.ResultObjectVo;
import com.huilianyi.middleware.vo.SystemUserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * UserController.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午5:29
 */
@Controller
@RequestMapping("/face/user")
public class UserController {

    @Resource
    private LocaleMessageSource localeMessageSource;
    @Resource
    private SystemUserService userService;

    /**
     * 用户登录
     *
     * @param login 用户
     * @return 用户信息
     */
    @RequestMapping("/toLogin")
    @ResponseBody
    public ResultObjectVo<SystemUserVo> login(@RequestBody LoginVo login) {
        ResultObjectVo<SystemUserVo> returnValue = new ResultObjectVo<>();
        List<SystemUser> list = userService.selectByUsername(login.getUsername());
        if (null != list && list.size() == CommonValue.ONE) {
            if (list.get(CommonValue.ZERO).getPassword().equals(SecureUtil.sha1(login.getUsername() + CommonValue.SYSTEM_COLON + login.getPassword()))) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                returnValue.setObject(new SystemUserVo().changePoToVo(list.get(0)));
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("user.login.password.error"));
            }
        } else {
            returnValue.setMessage(localeMessageSource.getMessage("user.login.username.notExist"));
        }
        return returnValue;
    }

    /**
     * 退出登录
     *
     * @return logout
     */
    @RequestMapping("/logout")
    @ResponseBody
    public CommonResultVo logout(@RequestBody String userId) {
        CommonResultVo returnValue = new CommonResultVo();
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        return returnValue;
    }

    /**
     * 新增用户信息
     *
     * @param user user
     * @return result
     */
    @RequestMapping("/add")
    @ResponseBody
    public CommonResultVo add(@RequestBody SystemUser user) {
        CommonResultVo returnValue = new CommonResultVo();
        if (StrUtil.isEmpty(user.getObjId())) {
            List<SystemUser> list = userService.selectByUsername(user.getUsername());
            if (null != list && !list.isEmpty()) {
                returnValue.setMessage(localeMessageSource.getMessage("user.add.username.existed"));
            } else {
                Integer result = userService.save(user);
                if (CommonValue.ONE.equals(result)) {
                    returnValue.setSuccess(true);
                    returnValue.setCode(CommonValue.SUCCESS_CODE);
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("user.add.failed"));
                }
            }
        } else {
            returnValue = update(user);
        }
        return returnValue;
    }

    /**
     * 修改用户信息
     *
     * @param user user
     * @return result
     */
    @RequestMapping("/update")
    @ResponseBody
    public CommonResultVo update(@RequestBody SystemUser user) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemUser userInfo = userService.selectByObjId(user.getObjId());
        if (null == userInfo) {
            returnValue.setMessage(localeMessageSource.getMessage("user.update.username.not.existed"));
        } else if (CommonValue.SYSTEM_DEFAULT_ADMIN.equals(userInfo.getUsername())) {
            returnValue.setMessage(localeMessageSource.getMessage("user.update.failed.admin"));
        } else {
            user.setCreateBy(userInfo.getCreateBy());
            Integer result = userService.update(user);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("user.update.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 修改密码
     *
     * @param user user
     * @return result
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public CommonResultVo updatePassword(@RequestBody SystemUser user) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemUser userInfo = userService.selectByObjId(user.getObjId());
        if (null == userInfo) {
            returnValue.setMessage(localeMessageSource.getMessage("user.update.username.not.existed"));
        } else {
            userInfo.setPassword(user.getPassword());
            Integer result = userService.updatePassword(userInfo);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("user.update.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 删除用户信息
     *
     * @param objId objId
     * @return result
     */
    @RequestMapping("/delete")
    @ResponseBody
    public CommonResultVo delete(@RequestBody String objId) {
        CommonResultVo returnValue = new CommonResultVo();
        SystemUser user = userService.selectByObjId(objId);
        if (null == user) {
            returnValue.setMessage(localeMessageSource.getMessage("user.delete.username.not.existed"));
        } else if (CommonValue.SYSTEM_DEFAULT_ADMIN.equals(user.getUsername())) {
            returnValue.setMessage(localeMessageSource.getMessage("user.delete.admin"));
        } else {
            Integer result = userService.delete(objId);
            if (CommonValue.ONE.equals(result)) {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                returnValue.setMessage(localeMessageSource.getMessage("user.delete.success"));
            } else {
                returnValue.setMessage(localeMessageSource.getMessage("user.delete.failed"));
            }
        }
        return returnValue;
    }

    /**
     * 查询用户
     *
     * @param params params
     * @return list
     */
    @RequestMapping("/select")
    @ResponseBody
    public ResultListVo<SystemUser> select(@RequestParam Map<String, Object> params) {
        ResultListVo<SystemUser> returnValue = new ResultListVo<>();

        int pageNo = null == params.get("page") ? CommonValue.ONE : Integer.valueOf(String.valueOf(params.get("page")));
        int limit = null == params.get("limit") ? CommonValue.TEN : Integer.valueOf(String.valueOf(params.get("limit")));

        EntityWrapper<SystemUser> wrapper = new EntityWrapper<>();
        if (params.size() > CommonValue.TWO) {
            wrapper.setEntity(new SystemUser());
            wrapper.where(CommonValue.SQL_WHERE);
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.USER_SEARCH_COLUMN_NAME)))) {
                wrapper.like(CommonValue.USER_DB_COLUMN_NAME, String.valueOf(params.get(CommonValue.USER_SEARCH_COLUMN_NAME)));
            }
            if (StrUtil.isNotEmpty(String.valueOf(params.get(CommonValue.USER_SEARCH_COLUMN_USERNAME)))) {
                wrapper.like(CommonValue.USER_DB_COLUMN_USERNAME, String.valueOf(params.get(CommonValue.USER_SEARCH_COLUMN_USERNAME)));
            }
        }
        wrapper.orderBy(CommonValue.USER_DEFAULT_ORDER_COLUMN);

        Page<SystemUser> page = new Page<>(pageNo, limit);
        page = userService.select(page, wrapper);
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        returnValue.setList(page.getRecords());
        returnValue.setTotal(page.getTotal());
        return returnValue;
    }

    /**
     * 删除用户信息
     *
     * @param objIds objIds
     * @return result
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public CommonResultVo deleteBatch(@RequestBody List<String> objIds) {
        CommonResultVo returnValue = new CommonResultVo();
        List<SystemUser> admins = userService.selectByUsername(CommonValue.SYSTEM_DEFAULT_ADMIN);
        boolean flag = false;
        for (SystemUser admin : admins) {
            if (objIds.contains(admin.getObjId())) {
                objIds.remove(admin.getObjId());
                flag = true;
            }
        }
        if (objIds.isEmpty() && flag) {
            returnValue.setMessage(localeMessageSource.getMessage("user.delete.admin"));
        } else {
            Integer result = userService.deleteBatch(objIds);
            if (CommonValue.ZERO.equals(result)) {
                returnValue.setMessage(localeMessageSource.getMessage("user.delete.failed"));
            } else {
                returnValue.setSuccess(true);
                returnValue.setCode(CommonValue.SUCCESS_CODE);
                if (flag) {
                    returnValue.setMessage(localeMessageSource.getMessage("user.delete.success.not.admin"));
                } else {
                    returnValue.setMessage(localeMessageSource.getMessage("user.delete.success"));
                }
            }
        }
        return returnValue;
    }
}

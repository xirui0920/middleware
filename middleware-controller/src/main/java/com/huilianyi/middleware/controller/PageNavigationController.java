package com.huilianyi.middleware.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.common.PageNavigation;
import com.huilianyi.middleware.po.*;
import com.huilianyi.middleware.service.SystemMenuService;
import com.huilianyi.middleware.service.SystemRoleService;
import com.huilianyi.middleware.service.SystemTaskService;
import com.huilianyi.middleware.service.SystemUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * PageNavigationController.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/23 上午11:40
 */
@Controller
@RequestMapping("/page/navigation")
public class PageNavigationController {

    @Resource
    private SystemUserService userService;
    @Resource
    private SystemRoleService roleService;
    @Resource
    private SystemMenuService menuService;
    @Resource
    private SystemTaskService taskService;
    @Resource
    private LocaleResolver localeResolver;

    /**
     * 登录页面
     *
     * @return login
     */
    @RequestMapping("/login")
    public String login() {
        return PageNavigation.LOGIN;
    }

    /**
     * 首页
     *
     * @return index
     */
    @RequestMapping("/index")
    public String index(String userId, String locale, Model model, HttpServletRequest request, HttpServletResponse response) {
        setUserLocale(locale, request, response);
        model.addAttribute("year", DateUtil.year(new Date()));
        model.addAttribute("systemName", CommonValue.SYSTEM_NAME);
        model.addAttribute("userMenuList", menuService.selectUserMenu(userId));
        return PageNavigation.INDEX;
    }

    /**
     * @return string
     */
    @RequestMapping("/fontAwesomeIcons")
    public String fontAwesomeIcons() {
        return PageNavigation.FONT_AWESOME_ICONS;
    }

    /**
     * @return string
     */
    @RequestMapping("/cron")
    public String cron() {
        return PageNavigation.CRON;
    }

    /**
     * 用户管理
     *
     * @return user
     */
    @RequestMapping("/user")
    public String user() {
        return PageNavigation.USER;
    }

    /**
     * 用户查看
     *
     * @param objId objId
     * @return user
     */
    @RequestMapping("/user/view")
    public String userView(String objId, Model model) {
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_VIEW);
        model.addAttribute("userInfo", userService.selectByObjId(objId));
        return PageNavigation.USER_INFO;
    }

    /**
     * 用户编辑
     *
     * @param objId objId
     * @return user
     */
    @RequestMapping("/user/edit")
    public String userEdit(String objId, Model model) {
        SystemUser user = new SystemUser();
        if (StrUtil.isNotEmpty(objId)) {
            user = userService.selectByObjId(objId);
        }
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_EDIT);
        model.addAttribute("userInfo", user);
        return PageNavigation.USER_INFO;
    }

    /**
     * 用户关联角色
     *
     * @param objId objId
     * @return user
     */
    @RequestMapping("/user/relRole")
    public String userRelRole(String objId, Model model) {
        SystemUser role = userService.selectByObjId(objId);
        model.addAttribute("userInfo", role);
        SystemUserRole userRole = userService.selectUserRole(objId);
        model.addAttribute("roleId", null == userRole ? "" : userRole.getRoleId());
        List<SystemRole> roleList = roleService.selectAllEnableRoles();
        model.addAttribute("roleList", roleList);
        return PageNavigation.USER_REL_ROLE;
    }

    /**
     * 角色管理
     *
     * @return role
     */
    @RequestMapping("/role")
    public String role() {
        return PageNavigation.ROLE;
    }

    /**
     * 角色信息查看
     *
     * @param objId objId
     * @return role
     */
    @RequestMapping("/role/view")
    public String roleView(String objId, Model model) {
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_VIEW);
        model.addAttribute("roleInfo", roleService.selectByObjId(objId));
        return PageNavigation.ROLE_INFO;
    }

    /**
     * 角色信息编辑
     *
     * @param objId objId
     * @return role
     */
    @RequestMapping("/role/edit")
    public String roleEdit(String objId, Model model) {
        SystemRole role = new SystemRole();
        if (StrUtil.isNotEmpty(objId)) {
            role = roleService.selectByObjId(objId);
        }
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_EDIT);
        model.addAttribute("roleInfo", role);
        return PageNavigation.ROLE_INFO;
    }

    /**
     * 角色关联菜单
     *
     * @param objId objId
     * @return role
     */
    @RequestMapping("/role/relMenu")
    public String roleRelMenu(String objId, Model model) {
        SystemRole role = roleService.selectByObjId(objId);
        model.addAttribute("roleInfo", role);
        return PageNavigation.ROLE_REL_MENU;
    }

    /**
     * 菜单管理
     *
     * @return menu
     */
    @RequestMapping("/menu")
    public String menu() {
        return PageNavigation.MENU;
    }

    /**
     * 菜单信息查看
     *
     * @param objId objId
     * @return menu
     */
    @RequestMapping("/menu/view")
    public String menuView(String objId, Model model) {
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_VIEW);
        model.addAttribute("menuInfo", menuService.selectByObjId(objId));
        model.addAttribute("pathMenuList", menuService.selectPathMenus());
        return PageNavigation.MENU_INFO;
    }

    /**
     * 菜单信息编辑
     *
     * @param objId objId
     * @return menu
     */
    @RequestMapping("/menu/edit")
    public String menuEdit(String objId, Model model) {
        SystemMenu menu = new SystemMenu();
        if (StrUtil.isNotEmpty(objId)) {
            menu = menuService.selectByObjId(objId);
        }
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_EDIT);
        model.addAttribute("menuInfo", menu);
        model.addAttribute("pathMenuList", menuService.selectPathMenus());
        return PageNavigation.MENU_INFO;
    }

    /**
     * 任务管理
     *
     * @return task
     */
    @RequestMapping("/task")
    public String task() {
        return PageNavigation.TASK;
    }

    /**
     * 任务信息查看
     *
     * @param objId objId
     * @return task
     */
    @RequestMapping("/task/view")
    public String taskView(String objId, Model model) {
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_VIEW);
        model.addAttribute("taskInfo", taskService.selectByObjId(objId));
        return PageNavigation.TASK_INFO;
    }

    /**
     * 任务信息编辑
     *
     * @param objId objId
     * @return task
     */
    @RequestMapping("/task/edit")
    public String taskEdit(String objId, Model model) {
        SystemTask task = new SystemTask();
        if (StrUtil.isNotEmpty(objId)) {
            task = taskService.selectByObjId(objId);
        }
        model.addAttribute(CommonValue.PAGE_PROPERTY, CommonValue.PAGE_PROPERTY_EDIT);
        model.addAttribute("taskInfo", task);
        return PageNavigation.TASK_INFO;
    }

    private void setUserLocale(String localeStr, HttpServletRequest request, HttpServletResponse response) {
        Locale locale = getLocaleFromStr(localeStr);
        if (locale == null) {
            return;
        }
        localeResolver.setLocale(request, response, locale);
    }

    private Locale getLocaleFromStr(String str) {
        if (str == null) {
            return null;
        }
        String[] p = StrUtil.split(str, "_");
        Locale locale;
        switch (p.length) {
            case 2:
                locale = new Locale(p[0], p[1]);
                break;
            case 3:
                locale = new Locale(p[0], p[1], p[2]);
                break;
            default:
                locale = new Locale(p[0]);
        }
        return locale;
    }
}
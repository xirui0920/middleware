package com.huilianyi.middleware.common;

/**
 * PageNavigation.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/21 下午3:29
 */
public interface PageNavigation {
    /**
     * 登录页面
     */
    String LOGIN = "login";
    /**
     * 主页
     */
    String INDEX = "index";
    /**
     * 用户管理页
     */
    String USER = "user";
    /**
     * 用户信息页
     */
    String USER_INFO = "userInfo";
    /**
     * 用户信息页
     */
    String USER_REL_ROLE = "userRelRole";
    /**
     * 角色管理页
     */
    String ROLE = "role";
    /**
     * 角色信息页
     */
    String ROLE_INFO = "roleInfo";
    /**
     * 角色关联菜单页
     */
    String ROLE_REL_MENU = "roleRelMenu";
    /**
     * 菜单管理页
     */
    String MENU = "menu";
    /**
     * 菜单信息页
     */
    String MENU_INFO = "menuInfo";
    /**
     * 任务管理页
     */
    String TASK = "task";
    /**
     * 任务信息页
     */
    String TASK_INFO = "taskInfo";
    /**
     * 图标页
     */
    String FONT_AWESOME_ICONS = "fontAwesomeIcons";
    /**
     * Cron表达式生成页
     */
    String CRON = "cron";
}

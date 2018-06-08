package com.huilianyi.middleware.common;

/**
 * CommonValue.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/22 上午9:18
 */
public interface CommonValue {
    String SYSTEM_COLON = ":";
    String SYSTEM_NAME = "Helios";
    String SYSTEM_DEFAULT_ADMIN = "admin";
    String SYSTEM_DEFAULT_PASSWORD = "middleware";
    Integer SUCCESS_CODE = 10000;
    Integer FAILED_CODE = 99999;
    Integer ZERO = 0;
    Integer ONE = 1;
    Integer TWO = 2;
    Integer FIVE = 5;
    Integer TEN = 10;
    String PAGE_PROPERTY = "pageProperty";
    String PAGE_PROPERTY_VIEW = "view";
    String PAGE_PROPERTY_EDIT = "edit";
    String SQL_WHERE = "1=1";

    String USER_SEARCH_COLUMN_NAME = "name";
    String USER_DB_COLUMN_NAME = "NAME";
    String USER_SEARCH_COLUMN_USERNAME = "username";
    String USER_DB_COLUMN_USERNAME = "USERNAME";
    String USER_DEFAULT_ORDER_COLUMN = "USERNAME";

    String MENU_SEARCH_COLUMN_MENU_KEY = "menuKey";
    String MENU_DB_COLUMN_MENU_KEY = "MENU_KEY";
    String MENU_DB_COLUMN_MENU_URI = "MENU_URI";
    String MENU_SEARCH_COLUMN_MENU_TYPE = "menuType";
    String MENU_DB_COLUMN_MENU_TYPE = "MENU_TYPE";
    String MENU_DEFAULT_ORDER_COLUMN = "MENU_KEY";

    String ROLE_SEARCH_COLUMN_ROLE_NAME = "roleName";
    String ROLE_DB_COLUMN_ROLE_NAME = "ROLE_NAME";
    String ROLE_SEARCH_COLUMN_ROLE_NICKNAME = "roleNickname";
    String ROLE_DB_COLUMN_ROLE_NICKNAME = "ROLE_NICKNAME";
    String ROLE_DEFAULT_ORDER_COLUMN = "ROLE_NAME";

    String TASK_SEARCH_COLUMN_TASK_NAME = "taskName";
    String TASK_DB_COLUMN_TASK_NAME = "TASK_NAME";
    String TASK_DEFAULT_ORDER_COLUMN = "NEXT_TIME";
}

package com.huilianyi.middleware.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtil.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午4:18
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * set
     *
     * @param applicationContext applicationContext
     * @throws BeansException BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = null == SpringUtil.applicationContext ? applicationContext : SpringUtil.applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return applicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过类名获取对象
     *
     * @param className 类名
     * @return bean
     */
    public static Object getBean(String className) {
        return getApplicationContext().getBean(className);
    }

    /**
     * 通过类的实例获取对象
     *
     * @param clazz 类
     * @param <T>   泛型
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}

package com.huilianyi.middleware.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * LocaleMessageSource.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/22 上午9:43
 */
@Component
public class LocaleMessageSource {

    @Resource
    private MessageSource messageSource;

    /**
     * 获取本地国际化资源
     *
     * @param code ：对应messages配置的key.
     * @return message
     */
    public String getMessage(String code) {
        String[] args = null;
        return getMessage(code, args);
    }

    /**
     * 获取本地国际化资源
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return message
     */
    private String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    /**
     * 获取本地国际化资源
     *
     * @param code           ：对应messages配置的key.
     * @param args           : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return message
     */
    private String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}

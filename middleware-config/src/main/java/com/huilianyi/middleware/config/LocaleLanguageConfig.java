package com.huilianyi.middleware.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * LocaleLanguageConfig.java
 * 国际化配置
 *
 * @author : Gooliang Young
 * @date : 2018/4/21 下午10:07
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LocaleLanguageConfig extends MessageSourceAutoConfiguration {

    /**
     * 添加语言解析器，设置默认语言为简体中文
     *
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.CHINA);
        return sessionLocaleResolver;
    }

    /**
     * 添加切换语言过滤器
     *
     * @return LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        return localeChangeInterceptor;
    }
}

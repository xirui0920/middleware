package com.huilianyi.middleware.config;

import cn.hutool.core.date.DatePattern;
import com.huilianyi.middleware.common.PageNavigation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * WebMvcConfig.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/23 下午11:48
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    /**
     * 配置项目默认首页
     *
     * @param registry registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.info("配置项目默认访问主页[{}]...", PageNavigation.LOGIN);
        registry.addViewController(File.separator).setViewName("forward:/page/navigation/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 自动转换时间格式
     *
     * @param registry date
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter(DatePattern.NORM_DATETIME_PATTERN));
    }
}

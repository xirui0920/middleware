package com.huilianyi.middleware.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/11 下午1:23
 */

@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     *
     * @return paginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

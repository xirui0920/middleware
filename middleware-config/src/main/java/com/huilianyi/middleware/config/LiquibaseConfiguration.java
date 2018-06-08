package com.huilianyi.middleware.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author GooliangYoung
 */
@Configuration
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfiguration {

    @Resource
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase(LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SyncSpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setDropFirst(properties.isDropFirst());
        return liquibase;
    }

}

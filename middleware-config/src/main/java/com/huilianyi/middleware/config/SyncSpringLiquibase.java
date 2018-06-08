package com.huilianyi.middleware.config;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author GooliangYoung
 */
public class SyncSpringLiquibase extends SpringLiquibase {

    private static final Logger log = LoggerFactory.getLogger(SyncSpringLiquibase.class);

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        log.debug("==========开始 Liquibase 同步==========");
        initDb();
    }

    private void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        log.debug("==========完成 Liquibase 同步，耗时 {} 毫秒==========", watch.getTotalTimeMillis());
    }
}

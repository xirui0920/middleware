package com.huilianyi.middleware;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WebModelStarter.java
 *
 * @author : Gooliang Young
 * @date : 2018/5/20 下午5:54
 */
@SpringBootApplication(scanBasePackages = {"com.huilianyi.middleware"})
@MapperScan("com.huilianyi.middleware.dao")
public class WebModelStarter {
    private static final Logger logger = LoggerFactory.getLogger(WebModelStarter.class);

    public static void main(String[] args) {
        SpringApplication.run(WebModelStarter.class, args);
        logger.info("==============================web模块启动成功==============================");
    }
}

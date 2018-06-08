package com.huilianyi.middleware.starter;

import com.huilianyi.middleware.tasker.AutoTaskExecutor;
import com.huilianyi.middleware.util.SpringUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * TaskApplicationRunner.java
 *
 * @author : Gooliang Young
 * @date : 2018/4/22 下午9:25
 */
@Component
public class TaskApplicationRunner implements ApplicationRunner {
    /**
     * 启动后先扫描一次需要执行的定时任务
     *
     * @param arguments arguments
     */
    @Override
    public void run(ApplicationArguments arguments) {
        SpringUtil.getBean(AutoTaskExecutor.class).start();
    }
}

package com.inori.executor.configure;

import com.inori.executor.ApplicationContextHolder;
import com.inori.executor.controller.Executor;
import com.inori.executor.service.ExecutorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExecutorAutoConfiguration
 *
 * @author inori
 * @date 2020/12/22
 */
@Configuration
//@EnableAutoConfiguration
@ConditionalOnProperty(value = "executor.enabled")
//@ComponentScan("com.inori.executor")
public class ExecutorAutoConfiguration {

    @Bean
    public Executor executor(ExecutorService executorService) {
        return new Executor(executorService);
    }

    @Bean
    public ExecutorService executorService() {
        return new ExecutorService();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}

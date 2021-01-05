package com.inori.executor.configure;

import com.inori.executor.controller.Executor;
import com.inori.executor.service.ExecutorService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ExecutorAutoConfiguration
 *
 * @author inori
 * @date 2020/12/22
 */
@Configuration
@EnableAutoConfiguration
//@ConditionalOnProperty(value = "executor.enabled")
//@ComponentScan("com.inori.executor")
public class ExecutorAutoConfiguration {

//    @Bean
//    public Executor executor(ExecutorService executorService) {
//        return new Executor(executorService);
//    }
//
//
//    @Bean
//    public ExecutorService executorService() {
//        return new ExecutorService();
//    }
}

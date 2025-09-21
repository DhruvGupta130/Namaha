package com.trulydesignfirm.namaha.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    /// Virtual thread executor for lightweight concurrent tasks.
    /// Can be injected anywhere for parallel processing.
    @Bean(destroyMethod = "shutdown")
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    /// TaskScheduler that runs @Scheduled tasks on virtual threads.
    /// Java 21+ required.
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setVirtualThreads(true);
        scheduler.setThreadNamePrefix("virtual-scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
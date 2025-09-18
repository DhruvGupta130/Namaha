package com.trulydesignfirm.namaha.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public Executor applicationExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}

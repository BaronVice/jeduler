package com.bv.pet.jeduler.config;

import com.bv.pet.jeduler.services.mail.MailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MailServiceConfiguration {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("MailServiceTaskScheduler");

        return threadPoolTaskScheduler;
    }

    @Bean
    public Map<Long, Instant> instants(){
        return new ConcurrentHashMap<>(50);
    }

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger(MailServiceImpl.class);
    }
}

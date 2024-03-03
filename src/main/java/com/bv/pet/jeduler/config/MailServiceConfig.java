package com.bv.pet.jeduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MailServiceConfig {
    @Bean(name = "telegramSender")
    public ThreadPoolTaskScheduler notificationSender(){
        return taskSchedulerCreator(3, "NotificationSenderThread");
    }

    @Bean(name = "tokenDisposerScheduler")
    public ThreadPoolTaskScheduler oneTimeTokenDisposer(){
        return taskSchedulerCreator(2, "OneTimeTokenCleanerThread");
    }

    private ThreadPoolTaskScheduler taskSchedulerCreator(int poolSize, String threadPrefix){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(poolSize);
        threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
        threadPoolTaskScheduler.setThreadNamePrefix(threadPrefix);

        return threadPoolTaskScheduler;
    }

    @Bean
    public Map<Long, Instant> instants(){
        return new ConcurrentHashMap<>(64);
    }
}

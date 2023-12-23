package com.bv.pet.jeduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class AsyncConfig {
    @Bean
    @Scope("prototype")
    public Lock lock(){
        return new ReentrantLock();
    }

    @Bean
    @Scope("prototype")
    public AtomicLong atomicInteger(){
        return new AtomicLong(0);
    }
}

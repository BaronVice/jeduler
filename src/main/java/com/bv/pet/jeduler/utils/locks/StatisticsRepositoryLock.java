package com.bv.pet.jeduler.utils.locks;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
public class StatisticsRepositoryLock extends RepositoryLock {
    public StatisticsRepositoryLock(Lock lock) {
        super(lock);
    }
}

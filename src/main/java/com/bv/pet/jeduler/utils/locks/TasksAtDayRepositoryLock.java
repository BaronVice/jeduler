package com.bv.pet.jeduler.utils.locks;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Component
public class TasksAtDayRepositoryLock extends RepositoryLock {
    public TasksAtDayRepositoryLock(Lock lock) {
        super(lock);
    }
}

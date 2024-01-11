package com.bv.pet.jeduler.application.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Getter
@Setter
@Component
public class UserAmount {
    private static final Logger logger = LoggerFactory.getLogger(UserAmount.class);
    private final Lock lock;
    private short amount;

    @Autowired
    public UserAmount(Lock lock) {
        this.lock = lock;
        this.amount = 0;
    }

    @Async
    public void increment(){
        changeAmount((short) 1);
    }

    @Async
    public void decrement(){
        changeAmount((short) -1);
    }

    @SneakyThrows
    private void changeAmount(short val){
        boolean isAcquired = lock.tryLock(5, TimeUnit.SECONDS);
        if (isAcquired){
            try {
                amount = (short) (amount + val);
            } finally {
                lock.unlock();
            }
        } else {
            logger.info("Can't acquire lock");
        }
    }
}

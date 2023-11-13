package com.bv.pet.jeduler.utils.locks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.locks.Lock;

@Getter
@AllArgsConstructor
public abstract class RepositoryLock {
    protected final Lock lock;
}

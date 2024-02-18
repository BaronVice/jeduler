package com.bv.pet.jeduler.services.mock.pools;

import com.bv.pet.jeduler.entities.ApplicationEntity;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ObjectPool<T extends ApplicationEntity<?>>{
    private long expirationTime;
    private Map<T, Long> locked, unlocked;
    protected Random random;
    protected Faker faker;


    public ObjectPool() {
        this.expirationTime = 30000; // 30 seconds
        this.locked = new ConcurrentHashMap<>();
        this.unlocked = new ConcurrentHashMap<>();
        this.random = new Random();
        this.faker = new Faker();
    }

    protected abstract T create();

    protected abstract void expire(T o);

    protected abstract void nullIds(T o);

    public synchronized List<T> checkOut(int n){
        List<T> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(checkOut());
        }
        return list;
    }

    public T checkOut() {
        long now = System.currentTimeMillis();
        T t;
        if (unlocked.size() > 0) {
            for (Map.Entry<T, Long> pair : unlocked.entrySet()) {
                t = pair.getKey();
                if ((now - pair.getValue()) > expirationTime) {
                    // object has expired
                    unlocked.remove(t);
                    expire(t);
                    t = null;
                } else {
                    unlocked.remove(t);
                    nullIds(t);
                    locked.put(t, now);
                    return t;
                }
            }
        }
        // no objects available, create a new one
        t = create();
        locked.put(t, now);
        return (t);
    }

    public synchronized void checkIn(T t) {
        locked.remove(t);
        unlocked.put(t, System.currentTimeMillis());
    }

    public synchronized void checkIn(List<T> list) {
        for (T t: list) {
            locked.remove(t);
            unlocked.put(t, System.currentTimeMillis());
        }
    }
}
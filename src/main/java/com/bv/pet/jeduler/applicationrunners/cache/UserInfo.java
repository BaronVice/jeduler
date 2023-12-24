package com.bv.pet.jeduler.applicationrunners.cache;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class UserInfo {
    private final Map<Short, Short> info;

    public UserInfo(){
        info = new ConcurrentHashMap<>(64);
    }

    public void changeValue(short key, short value){
        info.merge(
                key,
                value,
                (a, b) -> (short) (a + b)
        );
    }
}

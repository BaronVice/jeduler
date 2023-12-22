package com.bv.pet.jeduler.applicationrunners.data;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class UserInfo {
    private final Map<Short, Short> info;

    public UserInfo(){
        info = new ConcurrentHashMap<>(64);
    }
}

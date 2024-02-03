package com.bv.pet.jeduler.application.cache;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;
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

    public void setValue(short key, short value){
        info.put(key, value);
    }

    public short getOrElseZero(short key){
        Optional<Short> op = Optional.ofNullable(info.get(key));
        return op.orElse((short) 0);

    }
}

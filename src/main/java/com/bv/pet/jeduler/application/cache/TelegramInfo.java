package com.bv.pet.jeduler.application.cache;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class TelegramInfo {
    Map<Short, Long> usersChatId;
    Map<String, Short> tokenHolder;

    public TelegramInfo() {
        usersChatId = new ConcurrentHashMap<>();
        tokenHolder = new ConcurrentHashMap<>();
    }
}

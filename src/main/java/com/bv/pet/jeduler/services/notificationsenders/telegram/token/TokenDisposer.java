package com.bv.pet.jeduler.services.notificationsenders.telegram.token;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenDisposer {
    @Qualifier("tokenDisposerScheduler")
    private final ThreadPoolTaskScheduler disposer;

    public void scheduleDispose(
        Map<String, Short> codeToUser,
        String token
    ){
        disposer.schedule(
                new DisposeTokenTask(codeToUser, token),
                Instant.now().plusSeconds(120)
        );
    }
}

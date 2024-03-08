package com.bv.pet.jeduler.services.notificationsenders.telegram.token;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@AllArgsConstructor
public class DisposeTokenTask implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(DisposeTokenTask.class);
    private final Map<String, Short> codeToUser;
    private final String token;

    @Override
    public void run() {
        Short userId = codeToUser.get(token);
        logger.info(
                ("token " + token + " has" + (userId == null ? "" : " not") + " been activated, disposing")
        );
        codeToUser.remove(token);
    }
}

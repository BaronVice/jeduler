package com.bv.pet.jeduler.services.notificationsenders.telegram.token;

import com.bv.pet.jeduler.application.cache.TelegramInfo;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenGenerator {
    private final TelegramInfo telegramInfo;
    private final TokenDisposer tokenDisposer;
    private final Faker faker;

    public String get(short userId){
        if (telegramInfo.getUsersChatId().get(userId) != null)
            throw new ApplicationException("User is already connected", HttpStatus.BAD_REQUEST);

        String token = getTokenIfExist(userId);
        return (token != null ? token : generateToken(userId));
    }

    private String getTokenIfExist(short userId){
        for (Map.Entry<String, Short> entry : telegramInfo.getTokenHolder().entrySet()){
            if (entry.getValue() == userId)
                return entry.getKey();
        }

        return null;
    }

    private String generateToken(short userId){
        String token = faker.regexify("[A-Za-z0-9]{20}");
        telegramInfo.getTokenHolder().put(token, userId);
        tokenDisposer.scheduleDispose(
                telegramInfo.getTokenHolder(),
                token
        );

        return token;
    }
}

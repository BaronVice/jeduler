package com.bv.pet.jeduler.services.notificationsenders.telegram.token;

import com.bv.pet.jeduler.application.cache.TelegramInfo;
import com.bv.pet.jeduler.entities.TelegramChat;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.TelegramChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenConnector {
    private final TelegramInfo telegramInfo;
    private final TelegramChatRepository repository;

    @Transactional
    public boolean connect(long chatId, String token){
        if (token == null)
            return false;

        Short userId = telegramInfo.getTokenHolder().get(token);
        if (userId == null)
            return false;

        saveConnection(userId, chatId);
        telegramInfo.getTokenHolder().remove(token);
        return true;
    }

    @Async
    public void saveConnection(short userId, long chatId){
        repository.save(
                new TelegramChat(
                        chatId,
                        User.builder().id(userId).build()
                )
        );

        telegramInfo.getUsersChatId().put(userId, chatId);
    }

    @Transactional(readOnly = true)
    public boolean isConnected(long chatId){
        return repository.findById(chatId).isPresent();
    }
}

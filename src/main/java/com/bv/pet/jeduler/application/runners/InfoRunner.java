package com.bv.pet.jeduler.application.runners;

import com.bv.pet.jeduler.application.cache.*;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.TelegramChatRepository;
import com.bv.pet.jeduler.repositories.UserRepository;
import com.bv.pet.jeduler.repositories.projections.chat.UserChat;
import com.bv.pet.jeduler.repositories.projections.user.UserIdCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class InfoRunner implements ApplicationRunner {
    private final UserInfoCategories userInfoCategories;
    private final UserInfoTasks userInfoTasks;
    private final UserAmount userAmount;
    private final TelegramInfo telegramInfo;

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TelegramChatRepository telegramChatRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        insertUserId(userInfoCategories, userInfoTasks);
        populate(userInfoCategories, categoryRepository);
        populate(userInfoTasks, taskRepository);
        countUsers();
        collectChatIds();
    }

    public void insertUserId(UserInfo... userInfos){
        List<Short> list = userRepository.findIds();
        for (short id : list){
            for (UserInfo info : userInfos){
                info.getInfo().put(id, (short) 0);
            }
        }
    }

    public void populate(UserInfo userInfoCategories, UserIdCollector repository) {
        List<Short> list = repository.getUserIds();
        for (Short userId : list){
            userInfoCategories.changeValue(userId, (short) 1);
        }
    }

    private void countUsers() {
        userAmount.setAmount(
                (short) userRepository.count()
        );
    }

    private void collectChatIds() {
        List<UserChat> userChatList = telegramChatRepository.getAllBy();
        userChatList.forEach(
                pair -> telegramInfo.getUsersChatId().put(pair.getUserId(), pair.getId())
        );
    }
}

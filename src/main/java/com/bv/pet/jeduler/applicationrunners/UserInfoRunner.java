package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.applicationrunners.cache.UserAmount;
import com.bv.pet.jeduler.applicationrunners.cache.UserInfo;
import com.bv.pet.jeduler.applicationrunners.cache.UserInfoCategories;
import com.bv.pet.jeduler.applicationrunners.cache.UserInfoTasks;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.UserRepository;
import com.bv.pet.jeduler.repositories.projections.user.UserId;
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
public class UserInfoRunner implements ApplicationRunner {
    private final UserInfoCategories userInfoCategories;
    private final UserInfoTasks userInfoTasks;
    private final UserAmount userAmount;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        insertUserId(userInfoCategories, userInfoTasks);
        populate(userInfoCategories, categoryRepository);
        populate(userInfoTasks, taskRepository);
        countUsers();
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
        List<UserId> list = repository.findAllBy();
        for (UserId userId : list){
            userInfoCategories.changeValue(userId.getUser(), (short) 1);
        }
    }

    private void countUsers() {
        userAmount.setAmount(
                (short) userRepository.count()
        );
    }
}

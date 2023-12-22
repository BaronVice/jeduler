package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.applicationrunners.data.UserInfo;
import com.bv.pet.jeduler.applicationrunners.data.UserInfoCategories;
import com.bv.pet.jeduler.applicationrunners.data.UserInfoTasks;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.projections.UserId;
import com.bv.pet.jeduler.repositories.projections.UserIdCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInfoRunner implements ApplicationRunner {
    private final UserInfoCategories userInfoCategories;
    private final UserInfoTasks userInfoTasks;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(ApplicationArguments args) {
        populate(userInfoCategories, categoryRepository);
        populate(userInfoTasks, taskRepository);
    }

    @Transactional
    public void populate(UserInfo userInfoCategories, UserIdCollector repository) {
        List<UserId> list = repository.findAllBy();
        for (UserId userId : list){
            userInfoCategories.getInfo().putIfAbsent((short) 0, (short) 0);
            userInfoCategories.getInfo().merge(
                    userId.getId(),
                    (short) 1,
                    (a, b) -> (short) (a + b)
            );
        }
    }
}

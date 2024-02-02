package com.bv.pet.jeduler.config;

import com.bv.pet.jeduler.application.cache.*;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Generators;
import com.bv.pet.jeduler.services.mock.generators.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationInfoConfig {
    private final AdminInfo adminInfo;
    private final UserAmount userAmount;
    private final UserInfoCategories userInfoCategories;
    private final UserInfoTasks userInfoTasks;
    private final MockInfo mockInfo;

    private final UserGenerator userGenerator;
    private final CategoryGenerator categoryGenerator;
    private final NotificationGenerator notificationGenerator;
    private final TaskGenerator taskGenerator;
    private final SubtaskGenerator subtaskGenerator;

    @Bean
    public ApplicationInfo applicationInfo(){
        return new ApplicationInfo(
                adminInfo,
                userAmount,
                userInfoCategories,
                userInfoTasks,
                mockInfo
        );
    }

    @Bean
    public Generators generators(){
        return new Generators(
                userGenerator,
                categoryGenerator,
                notificationGenerator,
                taskGenerator,
                subtaskGenerator
        );
    }
}

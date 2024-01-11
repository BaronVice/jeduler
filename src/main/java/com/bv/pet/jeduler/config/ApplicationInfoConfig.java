package com.bv.pet.jeduler.config;

import com.bv.pet.jeduler.application.cache.*;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
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

    @Bean
    public ApplicationInfo applicationInfo(){
        return new ApplicationInfo(
                adminInfo,
                userAmount,
                userInfoCategories,
                userInfoTasks,
                new MockInfo()
        );
    }
}

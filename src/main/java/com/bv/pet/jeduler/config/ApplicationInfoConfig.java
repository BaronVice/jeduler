package com.bv.pet.jeduler.config;

import com.bv.pet.jeduler.application.cache.*;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Pools;
import com.bv.pet.jeduler.services.mock.pools.CategoryPool;
import com.bv.pet.jeduler.services.mock.pools.SubtaskPool;
import com.bv.pet.jeduler.services.mock.pools.TaskPool;
import com.bv.pet.jeduler.services.mock.pools.UserPool;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ApplicationInfoConfig {
    private final AdminInfo adminInfo;
    private final UserAmount userAmount;
    private final UserInfoCategories userInfoCategories;
    private final UserInfoTasks userInfoTasks;
    private final MockInfo mockInfo;

    private final UserPool userPool;
    private final TaskPool taskPool;
    private final CategoryPool categoryPool;
    private final SubtaskPool subtaskPool;

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
    public Pools pools(){
        return new Pools(
                userPool,
                taskPool,
                categoryPool,
                subtaskPool
        );
    }
}

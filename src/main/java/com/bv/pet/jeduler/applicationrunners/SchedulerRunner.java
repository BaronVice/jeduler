package com.bv.pet.jeduler.applicationrunners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class SchedulerRunner implements ApplicationRunner {

    // TODO: load notifications here
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}

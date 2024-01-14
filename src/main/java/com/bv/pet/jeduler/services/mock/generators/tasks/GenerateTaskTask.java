package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;

import java.util.Random;

public class GenerateTaskTask extends GenerateTask<Task> {
    @Override
    public void run() {
        Random random = new Random();
        int name = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int color = random.nextInt(100000, 999999);

//        getList().add(
//                Task.builder()
//                        .name()
//                        .description()
//                        .
//                        .build()
//        );
    }
}

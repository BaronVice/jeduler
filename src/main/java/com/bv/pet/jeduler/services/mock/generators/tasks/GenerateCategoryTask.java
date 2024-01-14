package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Category;

import java.util.Random;

public class GenerateCategoryTask extends GenerateTask<Category> {
    @Override
    public void run() {
        Random random = new Random();
        int name = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int color = random.nextInt(100000, 999999);

        getList().add(
                Category.builder()
                        .name(String.format("Mock%d", name))
                        .color(String.format("#%d", color))
                        .build()
        );
    }
}

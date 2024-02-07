package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Category;

import java.util.List;

public class GenerateCategoryTask extends GenerateTask<Category> {
    public GenerateCategoryTask(List<Category> list) {
        super(list);
    }

    @Override
    public void run() {
        generate();
    }

    @Override
    public void generate() {
        String name = faker.color().name() + random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int color = random.nextInt(100000, 999999);

        list.add(
                Category.builder()
                        .name(String.format("Mock%s", name))
                        .color(String.format("#%d", color))
                        .build()
        );
    }
}

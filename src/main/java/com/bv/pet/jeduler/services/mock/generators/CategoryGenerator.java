package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateCategoryTask;
import org.springframework.stereotype.Component;

@Component
public class CategoryGenerator extends Generator<Category, GenerateCategoryTask> {
    protected CategoryGenerator() {
        super(GenerateCategoryTask.class);
    }
}

package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateTaskTask;
import org.springframework.stereotype.Component;

@Component
public class TaskGenerator extends Generator<Task, GenerateTaskTask>{
    public TaskGenerator() {
        super(GenerateTaskTask.class);
    }
}

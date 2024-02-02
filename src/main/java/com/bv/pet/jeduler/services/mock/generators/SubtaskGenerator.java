package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateSubtaskTask;
import org.springframework.stereotype.Component;

@Component
public class SubtaskGenerator extends Generator<Subtask, GenerateSubtaskTask> {
    public SubtaskGenerator(){
        super(GenerateSubtaskTask.class);
    }
}

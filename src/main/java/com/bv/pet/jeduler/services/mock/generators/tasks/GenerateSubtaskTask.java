package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Subtask;

import java.util.List;

public class GenerateSubtaskTask extends GenerateTask<Subtask> {
    public GenerateSubtaskTask(List<Subtask> list){
        super(list);
    }

    @Override
    public void run() {


        list.add(
                Subtask.builder()
                        .name(faker.harryPotter().spell())
                        .isCompleted(random.nextBoolean())
                        .orderInList((short) list.size())
                        .build()
        );
    }
}

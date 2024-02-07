package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Task;

import java.time.Instant;
import java.util.List;

public class GenerateTaskTask extends GenerateTask<Task> {
    public GenerateTaskTask(List<Task> list){
        super(list);
    }

    @Override
    public void run() {
        generate();
    }

    @Override
    public void generate() {
        String name = faker.book().title();
        String description = faker.hobbit().location();
        short priority = (short) random.nextInt(1, 3);
        Instant startsAt = Instant.now().plusSeconds(random.nextInt(1000, 10000));
        Instant lastChanged = Instant.now().minusSeconds(random.nextInt(1, 10000));

        list.add(
                Task.builder()
                        .name(name)
                        .description(description)
                        .taskDone(false)
                        .priority(priority)
                        .startsAt(startsAt)
                        .lastChanged(lastChanged)
                        .build()
        );
    }
}

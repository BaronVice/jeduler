package com.bv.pet.jeduler.services.mock.pools;

import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskPool extends ObjectPool<Task> {
    private final SubtaskPool subtaskPool;

    public TaskPool(SubtaskPool subtaskPool) {
        super();
        this.subtaskPool = subtaskPool;
    }

    @Override
    protected Task create() {
        String name = faker.book().title();
        String description = faker.hobbit().location();
        short priority = (short) random.nextInt(1, 3);
        Instant startsAt = Instant.now().plusSeconds(random.nextInt(1000, 10000));
        Instant lastChanged = Instant.now().minusSeconds(random.nextInt(1, 10000));

        Task task = Task.builder()
                .name(name)
                .description(description)
                .taskDone(false)
                .priority(priority)
                .startsAt(startsAt)
                .lastChanged(lastChanged)
                .build();
        setSubtasks(task);

        return task;
    }

    private void setSubtasks(Task task){
        int i = random.nextInt(3, 9);
        task.setSubtasks(subtaskPool.checkOut(i));

        for (short j = 0; j < i; j++) {
            task.getSubtasks().get(j).setTask(task);
            task.getSubtasks().get(j).setOrderInList(j);
        }
    }

    @Override
    public void expire(Task o) {
        List<Subtask> subtasks = o.getSubtasks();
        subtasks.forEach(
                s -> s.setTask(null)
        );

        o.setSubtasks(null);
        subtaskPool.checkIn(subtasks);
    }

    @Override
    protected void nullIds(Task o) {
        o.setId(null);
        o.getSubtasks().forEach(
                subtaskPool::nullIds
        );
    }
}

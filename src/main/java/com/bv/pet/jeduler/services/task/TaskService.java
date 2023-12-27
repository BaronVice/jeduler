package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskMapper taskMapper;
    private final TaskServiceHandler handler;
    private final StatisticsService statistics;
    private final ApplicationInfo applicationInfo;

    @Override
    public TaskDto get(Integer id) {
        return taskMapper.toTaskDto(handler.get(id));
    }

    @Override
    public Integer create(short userId, String mail, TaskDto taskDto) {
        Task task = handler.create(userId, mail, taskDto);

        applicationInfo.userInfoTasks().changeValue(userId, (short) 1);
//        statistics.onTaskCreation(task);

        return task.getId();
    }

    @Override
    public void update(String mail, TaskDto taskDto) {
//        Task updated = taskMapper.toTask(taskDto);
//        Task toUpdate = handler.get(taskDto.getId());
//        boolean wasDone = toUpdate.isTaskDone();
//        Instant previousDate = toUpdate.getStartsAt();

        handler.update(mail, taskDto);
        // statistics.onTaskUpdate(updated, wasDone, previousDate);
    }

    @Override
    public void delete(short userId, Integer id) {
        handler.delete(id);
        applicationInfo.userInfoTasks().changeValue(
                userId,
                (short) -1
        );
    }
}

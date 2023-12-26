package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskMapper taskMapper;
    private final TaskServiceHandler handler;
    private final StatisticsService statistics;
    private final ApplicationInfo applicationInfo;

    @Override
    public List<TaskDto> all() {
        return taskMapper.toTaskDtoList(handler.getAll());
    }

    @Override
    public TaskDto get(Integer id) {
        return taskMapper.toTaskDto(handler.get(id));
    }

    @Override
    public Integer create(short userId, TaskDto taskDto) {
        Task task = handler.create(userId, taskDto);
        // Not now
//        mailService.handNotificationInScheduler(task);
        applicationInfo.userInfoTasks().changeValue(
                task.getUser().getId(),
                (short) 1
        );
//        statistics.onTaskCreation(task);

        return task.getId();
    }

    @Override
    public void update(TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Task toUpdate = handler.get(taskDto.getId());
        boolean wasDone = toUpdate.isTaskDone();
        Instant previousDate = toUpdate.getStartsAt();

        handler.update(updated);
        statistics.onTaskUpdate(updated, wasDone, previousDate);
    }

    @Override
    public void delete(Integer id) {
        handler.delete(id);
    }
}

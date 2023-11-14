package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskMapper taskMapper;
    private final TaskServiceHandler handler;
    private final StatisticsService statistics;

    @Override
    public List<TaskDto> all() {
        return taskMapper.toTaskDtoList(handler.getAll());
    }

    @Override
    public TaskDto get(Long id) {
        return taskMapper.toTaskDto(handler.get(id));
        // If guest - take ip, if requests from ip > 20 -> request captcha
    }

    @Override
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        handler.create(task);
        statistics.onTaskCreation(task);

        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto update(TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Task toUpdate = handler.get(taskDto.getId());
        boolean wasDone = toUpdate.isTaskDone();

        handler.update(updated);
        statistics.onTaskUpdate(updated, wasDone);

        return taskMapper.toTaskDto(toUpdate);
    }

    @Override
    public void delete(Long id) {
        handler.delete(id);
    }
}

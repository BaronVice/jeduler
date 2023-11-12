package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskMapper taskMapper;
    private final TaskServiceHandler handler;

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> all() {
        return taskMapper.toTaskDtoList(handler.getAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto get(Long id) {
        return taskMapper.toTaskDto(handler.get(id));
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        handler.create(task);

        return taskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Task toUpdate = handler.get(taskDto.getId());

        handler.update(updated, toUpdate);

        return taskMapper.toTaskDto(toUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        handler.delete(id);
    }
}

package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.services.handlers.TaskServiceHandler;
import com.bv.pet.jeduler.services.interfaces.ITaskService;
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
        handler.setNotificationOnTaskCreate(task);
        handler.setSubtasksOnTaskCreate(task);

        handler.saveTask(task);
        handler.saveSubtasks(task.getSubtasks());

        handler.handNotificationInMailService(task);

        return taskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Task toUpdate = handler.get(taskDto.getId());

        handler.setNotificationOnTaskUpdate(updated, toUpdate);
        handler.setSubtasksOnTaskUpdate(updated, toUpdate);

        toUpdate.setName(updated.getName());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setStartsAt(updated.getStartsAt());
        toUpdate.setExpiresAt(updated.getExpiresAt());
        toUpdate.setCategories(updated.getCategories());
        toUpdate.setSubtasks(updated.getSubtasks());
//        toUpdate.setNotification(updated.getNotification());

        handler.saveTask(toUpdate);

        return taskMapper.toTaskDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        handler.delete(id);
    }
}

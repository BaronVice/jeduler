package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.interfaces.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> all() {
        return TaskMapper.toTaskDtoList(taskRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto get(Long id) {
        return TaskMapper.toTaskDto(
                taskRepository.findById(id).orElseThrow(
                        () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
                )
        );
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        Task task = TaskMapper.toTask(taskDto);
        task.getNotification().setTask(task);

        taskRepository.save(task);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto taskDto) {
        Task toUpdate = taskRepository.findById(taskDto.getId()).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );

        Task task = TaskMapper.toTask(taskDto);
        toUpdate.setName(task.getName());
        toUpdate.setDescription(task.getDescription());
        toUpdate.setStartsAt(task.getStartsAt());
        toUpdate.setExpiresAt(task.getExpiresAt());
        toUpdate.setCategories(task.getCategories());

        if (task.getNotification() == null) {
            toUpdate.setNotification(null);
        } else if (toUpdate.getNotification() == null) {
            toUpdate.setNotification(task.getNotification());
            task.getNotification().setTask(toUpdate);
        } else {
            toUpdate.getNotification().setNotifyAt(task.getNotification().getNotifyAt());
        }

        taskRepository.save(toUpdate);

        return TaskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}

package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskDto> all() {
        return taskMapper.toTaskDtoList(taskRepository.findAll());
    }

    @Override
    public TaskDto get(Long id) {
        return taskMapper.toTaskDto(
                taskRepository.findById(id).orElseThrow(
                        () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
                )
        );
    }

    @Override
    @Transactional
    public TaskDto create(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        taskRepository.save(task);

        return taskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public TaskDto update(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        taskRepository.save(task);

        return taskMapper.toTaskDto(task);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}

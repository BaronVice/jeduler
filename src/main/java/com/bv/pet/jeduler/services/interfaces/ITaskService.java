package com.bv.pet.jeduler.services.interfaces;

import com.bv.pet.jeduler.dtos.TaskDto;

import java.util.List;

public interface ITaskService {
    List<TaskDto> all();
    TaskDto get(Long id);
    TaskDto create(TaskDto taskDto);
    TaskDto update(TaskDto taskDto);
    void delete(Long id);
}

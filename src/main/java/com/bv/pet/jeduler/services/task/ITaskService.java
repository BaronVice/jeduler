package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;

import java.util.List;

public interface ITaskService {
    List<TaskDto> all();
    TaskDto get(Integer id);
    Integer create(TaskDto taskDto);
    void update(TaskDto taskDto);
    void delete(Integer id);
}

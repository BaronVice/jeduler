package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;


public interface ITaskService {
    TaskDto get(int id);
    Integer create(short userId, String mail, TaskDto taskDto);
    void update(String mail, TaskDto taskDto);
    void delete(short userId, Integer id);
}

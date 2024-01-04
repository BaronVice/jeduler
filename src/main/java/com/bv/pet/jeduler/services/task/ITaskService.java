package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ITaskService {
    TaskDto get(int id);
    List<TaskDto> get(List<Short> categoryIds);
    List<TaskDto> get(String name, int page, OrderType orderType);
    Integer create(short userId, String mail, TaskDto taskDto);
    void update(String mail, TaskDto taskDto);
    void delete(short userId, Integer id);
}

package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.controllers.task.OrderType;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ITaskService {
    TaskDto get(int id);
    List<TaskDto> get(
            short userId,
            String name,
            List<Short> priorities,
            List<Short> categories,
            boolean categoriesAny,
            Date from,
            Date to,
            int page,
            int size,
            OrderType orderType
    );
    Integer create(short userId, String mail, TaskDto taskDto);
    void update(short userId, String mail, TaskDto taskDto);
    void delete(short userId, Integer id);
}

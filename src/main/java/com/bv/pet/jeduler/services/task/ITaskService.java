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
            Optional<String> name,
            Optional<List<Short>> priorities,
            Optional<List<Short>> categories,
            Optional<Date> from,
            Optional<Date> to,
            int page,
            OrderType orderType
    );
    Integer create(short userId, String mail, TaskDto taskDto);
    void update(short userId, String mail, TaskDto taskDto);
    void delete(short userId, Integer id);
}

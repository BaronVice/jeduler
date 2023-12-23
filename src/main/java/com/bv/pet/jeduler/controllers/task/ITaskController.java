package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskController {
    ResponseEntity<List<TaskDto>> allTasks();
    ResponseEntity<TaskDto> getTask(Integer id);
    ResponseEntity<Integer> createTask(TaskDto taskDto);
    ResponseEntity<?> updateTask(TaskDto taskDto);
    ResponseEntity<?> deleteTask(Integer id);
}

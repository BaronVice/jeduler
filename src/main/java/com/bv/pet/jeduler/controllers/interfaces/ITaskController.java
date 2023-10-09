package com.bv.pet.jeduler.controllers.interfaces;

import com.bv.pet.jeduler.dtos.TaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITaskController {
    ResponseEntity<List<TaskDto>> allTasks();
    ResponseEntity<TaskDto> getTask(Long id);
    ResponseEntity<TaskDto> createTask(TaskDto taskDto);
    ResponseEntity<TaskDto> updateTask(TaskDto taskDto);
    ResponseEntity<?> deleteTask(Long id);
}

package com.bv.pet.jeduler.controllers.interfaces;

import com.bv.pet.jeduler.dtos.TaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.TreeSet;

public interface ITaskController {
    ResponseEntity<TreeSet<TaskDto>> allTasks();
    ResponseEntity<TaskDto> getTask(Long id);
    ResponseEntity<TaskDto> createTask(TaskDto taskDto);
    ResponseEntity<TaskDto> updateTask(TaskDto taskDto);
    ResponseEntity<?> deleteTask(Long id);
}

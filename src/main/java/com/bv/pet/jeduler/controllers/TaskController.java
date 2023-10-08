package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController implements ITaskController{
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> allTasks(){
        return ResponseEntity.ok(taskService.all());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.get(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto createdTask = taskService.create(taskDto);

        return ResponseEntity
                .created(URI.create("/tasks" + taskDto.getId()))
                .body(createdTask);
    }

    @Override
    @PatchMapping
    public ResponseEntity<TaskDto> updateTask(TaskDto taskDto) {
        return null;
    }

    @Override
    @DeleteMapping
    public ResponseEntity<?> deleteTask(Long id) {
        return null;
    }
}

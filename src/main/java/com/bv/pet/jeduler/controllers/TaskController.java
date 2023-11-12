package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.controllers.interfaces.ITaskController;
import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.services.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController implements ITaskController {
    private final TaskService taskService;

    @Override
    @GetMapping
    public ResponseEntity<TreeSet<TaskDto>> allTasks(){
        return ResponseEntity.ok(
                new TreeSet<>(taskService.all())
        );
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
                .created(URI.create("/jeduler/tasks/" + createdTask.getId()))
                .body(createdTask);
    }

    @Override
    @PatchMapping
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.update(taskDto);

        return ResponseEntity
                .created(URI.create("/jeduler/tasks/" + updatedTask.getId()))
                .body(updatedTask);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}

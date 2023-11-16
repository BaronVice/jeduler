package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.dtos.TaskDto;
import com.bv.pet.jeduler.services.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController implements ITaskController {
    private final TaskService taskService;

    @Override
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
    public ResponseEntity<Long> createTask(@Valid @RequestBody TaskDto taskDto) {
        Long id = taskService.create(taskDto);

        return ResponseEntity
                .created(URI.create("/jeduler/tasks/" + id))
                .body(id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDto taskDto) {
        taskService.update(taskDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}

package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OrderType type
    ){
        return ResponseEntity.ok(type);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> allTasks(@RequestBody int[] ids){
        Arrays.stream(ids).forEach(System.out::println);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createTask(@Valid @RequestBody TaskDto taskDto) {
        Integer id = taskService.create(taskDto);

        return ResponseEntity
                .created(URI.create("/jeduler/tasks/" + id))
                .body(id);
    }

    @PatchMapping
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDto taskDto) {
        taskService.update(taskDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}

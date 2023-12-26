package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.task.ITaskService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController {
    private final ITaskService taskService;
    private final ApplicationInfo applicationInfo;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody TaskDto taskDto
    ) {
        short userId = userDetails.getUserId();
        Assert.assertAllowedAmount(
                applicationInfo.userInfoTasks().getInfo().get(userId),
                AllowedAmount.TASK
        );

        Integer id = taskService.create(userId, taskDto);
        return ResponseEntity.ok(id);
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

package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.SingleValueResponse;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.task.TaskService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ApplicationInfo applicationInfo;
    private final Assert anAssert;

    // Keep in mind that categoryIds and subtasks are unordered
    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(taskService.get(userDetails.getUserId(), id));
    }

    // Keep in mind that categoryIds and subtasks are unordered
    @GetMapping
    public ResponseEntity<?> getTasks(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "priorities", required = false) List<Short> priorities,
            @RequestParam(name = "categories", required = false) List<Short> categories,
            @RequestParam(name = "task_done", defaultValue = "Any") String taskDone,
            @RequestParam(name = "c_any", defaultValue = "0") boolean categoriesAny, // TODO: in next update perhaps
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm") Date from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm") Date to,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "order", defaultValue = "name") OrderType order
    ) {
        size = Math.min(size, 20);

        return ResponseEntity.ok(
                taskService.get(
                        userDetails.getUserId(),
                        name,
                        priorities,
                        categories,
                        categoriesAny,
                        taskDone,
                        from,
                        to,
                        page,
                        size,
                        order
                )
        );
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody TaskDto taskDto
    ) {
        short userId = userDetails.getUserId();

        anAssert.allowedCreation(
                applicationInfo.userInfoTasks().getInfo().get(userId),
                AllowedAmount.TASK
        );

        Integer id = taskService.create(userId, taskDto);
        return ResponseEntity.ok(new SingleValueResponse<>(id));
    }

    @PatchMapping
    public ResponseEntity<?> updateTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody TaskDto taskDto
    ) {
        short userId = userDetails.getUserId();
        taskService.update(
                userId,
                taskDto
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer id
    ) {
        taskService.delete(userDetails.getUserId(), id);
        return ResponseEntity.ok().build();
    }
}

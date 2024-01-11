package com.bv.pet.jeduler.controllers.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.CreateResponse;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.task.ITaskService;
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
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tasks")
public class TaskController {
    private final ITaskService taskService;
    private final ApplicationInfo applicationInfo;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.get(id));
    }

//    @GetMapping
//    public ResponseEntity<?> getTasks(@RequestParam(name = "categories", defaultValue = "") List<Short> ids){
//        return ResponseEntity.ok(taskService.get(ids));
//    }

    @GetMapping
    public ResponseEntity<?> getTask(
            // TODO: wrap in optionals perhaps?
            @RequestParam(name = "name") Optional<String> name,
            @RequestParam(name = "priorities") Optional<List<Short>> priorities,
            @RequestParam(name = "categories") Optional<List<Short>> categories,
            @RequestParam(name = "from") @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm") Optional<Date> from,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "dd.MM.yyyy-HH:mm") Optional<Date> to,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "order", defaultValue = "name") OrderType order
    ) {
        return ResponseEntity.ok(
                taskService.get(
                        name,
                        priorities,
                        categories,
                        from,
                        to,
                        page,
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
        String mail = userDetails.getUsername();

        Assert.assertAllowedCreation(
                applicationInfo.userInfoTasks().getInfo().get(userId),
                AllowedAmount.TASK
        );

        Integer id = taskService.create(userId, mail, taskDto);
        return ResponseEntity.ok(new CreateResponse<>(id));
    }

    @PatchMapping
    public ResponseEntity<?> updateTask(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody TaskDto taskDto
    ) {
        String mail = userDetails.getUsername();
        taskService.update(mail, taskDto);
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

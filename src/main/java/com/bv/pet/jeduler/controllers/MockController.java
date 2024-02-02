package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.services.mock.IMockService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/populate")
// TODO: return some info back in response
public class MockController {
    private final ApplicationInfo applicationInfo;
    private final IMockService populateService;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(
            @RequestParam(name = "amount", defaultValue = "1") int amount,
            @RequestParam(name = "amount", defaultValue = "1") int categoriesPerUser,
            @RequestParam(name = "tasks", defaultValue = "1") int tasksPerUser,
            @RequestParam(name = "subtasks", defaultValue = "1") int subtasksPerTask
    ){
        Assert.assertAllowedAmount(
                amount + applicationInfo.userAmount().getAmount(),
                AllowedAmount.USER
        );
        populateService.addUsers(
                amount,
                categoriesPerUser,
                tasksPerUser,
                subtasksPerTask
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> addTasks(
            @RequestParam(name = "userid", defaultValue = "-1") short userId,
            @RequestParam(name = "amount", defaultValue = "1") int amount,
            @RequestParam(name = "subtasks", defaultValue = "1") int subtasksPerTask
    ){
        populateService.addTasks(
                userId,
                amount,
                subtasksPerTask
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/categories")
    public ResponseEntity<?> addCategories(
            @RequestParam(name = "amount", defaultValue = "1") int amount,
            @RequestParam(name = "userids", defaultValue = "") List<Short> userIds,
            @RequestParam(name = "taskids", defaultValue = "") List<Integer> taskIds
    ){
        populateService.addCategories(
                amount,
                userIds,
                taskIds
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subtasks")
    public ResponseEntity<?> addSubtasks(
            @RequestParam(name = "taskid", defaultValue = "-1") int taskId,
            @RequestParam(name = "amount", defaultValue = "1") int amount
    ){
        populateService.addSubtasks(
                taskId,
                amount
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notification")
    public ResponseEntity<?> addNotification(
            @RequestParam(name = "taskid", defaultValue = "-1") int taskId,
            @RequestParam(name = "date") Optional<Date> date
    ){
        populateService.addNotification(
                taskId,
                date.orElse(new Date(System.currentTimeMillis() + 10000))
        );
        return ResponseEntity.ok().build();
    }
}

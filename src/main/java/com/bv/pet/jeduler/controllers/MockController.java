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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/populate")
// TODO: return some info back in response
public class MockController {
    private final ApplicationInfo applicationInfo;
    private final IMockService mockService;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(
            @RequestParam(name = "amount", defaultValue = "1") int amount
    ){
        Assert.assertAllowedAmount(
                amount + applicationInfo.userAmount().getAmount(),
                AllowedAmount.USER
        );
        mockService.addUsers(
                amount
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> addTasks(
            @RequestParam(name = "amount", defaultValue = "1") int amount,
            @RequestParam(name = "userid", defaultValue = "-1") short userId
    ){
        mockService.addTasks(
                amount,
                userId
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/categories")
    public ResponseEntity<?> addCategories(
            @RequestParam(name = "amount", defaultValue = "1") int amount,
            @RequestParam(name = "taskids", defaultValue = "-1") short userId
    ){
        // TODO: assert?
        mockService.addCategories(
                amount,
                userId
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subtasks")
    public ResponseEntity<?> addSubtasks(
            @RequestParam(name = "taskid", defaultValue = "-1") int taskId,
            @RequestParam(name = "amount", defaultValue = "1") int amount
    ){
        mockService.addSubtasks(
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
        mockService.addNotification(
                taskId,
                date.orElse(new Date(System.currentTimeMillis() + 10000))
        );
        return ResponseEntity.ok().build();
    }
}

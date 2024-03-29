package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.services.mock.MockService;
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
    private final MockService mockService;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(
            @RequestParam(name = "amount", defaultValue = "1") int amount
    ){
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
            @RequestParam(name = "userid", defaultValue = "-1") short userId
    ){
        mockService.addCategories(
                amount,
                userId
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notification")
    public ResponseEntity<?> addNotification(
            @RequestParam(name = "date") Optional<Date> date
    ){
        mockService.addNotification(
                date.orElse(new Date(System.currentTimeMillis() + 10000))
        );
        return ResponseEntity.ok().build();
    }
}

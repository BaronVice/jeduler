package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.services.user.UserService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/jeduler/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ApplicationInfo applicationInfo;
    private final Assert anAssert;

    @GetMapping("/categories")
    public ResponseEntity<?> categoriesInfo(){
        return ResponseEntity.ok(applicationInfo.userInfoCategories());
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> tasksInfo(){
        return ResponseEntity.ok(applicationInfo.userInfoTasks());
    }

    @GetMapping("/amount")
    public ResponseEntity<?> amountInfo(){
        return ResponseEntity.ok(applicationInfo.userAmount());
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        anAssert.allowedCreation(
                applicationInfo.userAmount().getAmount(),
                AllowedAmount.USER
        );

        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Short id){
        anAssert.notMainAdmin(id);

        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}

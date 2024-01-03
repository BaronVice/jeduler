package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.services.user.IUserService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/jeduler/admin")
@RequiredArgsConstructor
public class AdminController {
    private final IUserService userService;
    private final ApplicationInfo applicationInfo;

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

    @PostMapping("/create-user")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        Assert.assertAllowedAmount(
                applicationInfo.userAmount().getAmount(),
                AllowedAmount.USER
        );

        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> delete(@PathVariable Short id){
        Assert.assertNotAdmin(
                applicationInfo.adminInfo().getId(),
                id
        );

        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}

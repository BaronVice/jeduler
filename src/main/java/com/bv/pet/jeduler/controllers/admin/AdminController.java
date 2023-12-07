package com.bv.pet.jeduler.controllers.admin;

import com.bv.pet.jeduler.datacarriers.dtos.UserDto;
import com.bv.pet.jeduler.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/jeduler/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserDetailsService userDetailsService;
    private final IUserService userService;

    @GetMapping
    public UserDetails adminPage (Principal principal) {
        return userDetailsService.loadUserByUsername(principal.getName());
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        userService.save(userDto);
        return ResponseEntity.ok("Created");
    }
}

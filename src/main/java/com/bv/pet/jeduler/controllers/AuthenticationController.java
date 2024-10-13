package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.datacarriers.AuthenticationRequest;
import com.bv.pet.jeduler.services.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequiredArgsConstructor
//@RequestMapping("/jeduler")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        authenticationService.authenticate(
                authenticationRequest,
                request,
                response
        );

        return ResponseEntity.ok().build();
    }
}

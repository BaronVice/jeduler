package com.bv.pet.jeduler.controllers.authentication;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/jeduler/auth")
public class AuthenticationController implements IAuthenticationController{
    private final AuthenticationService authenticationService;

    @Override
    @PostMapping
    public ResponseEntity<?> authenticate(
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

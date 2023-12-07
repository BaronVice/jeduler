package com.bv.pet.jeduler.controllers.authentication;

import com.bv.pet.jeduler.datacarriers.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthenticationController {
    ResponseEntity<?> authenticate(
            AuthenticationRequest authenticationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    );
}

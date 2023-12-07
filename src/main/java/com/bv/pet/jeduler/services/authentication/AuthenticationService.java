package com.bv.pet.jeduler.services.authentication;

import com.bv.pet.jeduler.datacarriers.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    void authenticate(
        AuthenticationRequest authenticationRequest,
        HttpServletRequest request,
        HttpServletResponse response
    );
}

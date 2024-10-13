package com.bv.pet.jeduler.services.authentication;

import com.bv.pet.jeduler.datacarriers.AuthenticationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class SessionAuthenticationService implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Override
    public void authenticate(
            AuthenticationRequest authenticationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
            authenticationRequest.username(),
            authenticationRequest.password()
        );

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
}

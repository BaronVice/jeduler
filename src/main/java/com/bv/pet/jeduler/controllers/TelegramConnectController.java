package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.datacarriers.SingleValueResponse;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.notificationsenders.telegram.token.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tg")
public class TelegramConnectController {
    private final TokenGenerator tokenGenerator;

    @GetMapping
    public ResponseEntity<?> getToken(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return ResponseEntity.ok(
                new SingleValueResponse<>(tokenGenerator.get(userDetails.getUserId()))
        );
    }
}

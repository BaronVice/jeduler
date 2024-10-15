package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.datacarriers.SingleValueResponse;
import com.bv.pet.jeduler.services.notificationsenders.telegram.token.TokenGenerator;
import com.bv.pet.jeduler.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/tg")
public class TelegramConnectController {
    private final UserService userService;
    private final TokenGenerator tokenGenerator;

    @GetMapping
    public ResponseEntity<?> getToken(
            Principal principal
    ){
        short userId = userService.getIdOrElseCreateAndGet(principal.getName());
        return ResponseEntity.ok(
                new SingleValueResponse<>(tokenGenerator.get(userId))
        );
    }
}

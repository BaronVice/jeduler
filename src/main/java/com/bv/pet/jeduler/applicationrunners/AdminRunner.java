package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.bv.pet.jeduler.entities.user.Role.ADMIN;

@Component
@RequiredArgsConstructor
public class AdminRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    @Value("${custom.admin.username}")
    private String username;
    @Value("${custom.admin.password}")
    private String password;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(
                new User(
                        username,
                        password,
                        ADMIN
                )
        );
    }
}

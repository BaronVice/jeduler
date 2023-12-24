package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.applicationrunners.cache.AdminInfo;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.bv.pet.jeduler.entities.user.Role.ADMIN;

@Component
@RequiredArgsConstructor
@Order(1)
public class AdminRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final AdminInfo admin;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        createAdmin();
    }

    private void createAdmin() {
        if (userRepository.findByUsername(admin.getUsername()).isPresent())
            return;

        User user = new User(
                admin.getUsername(),
                admin.getPassword(),
                ADMIN
        );

        userRepository.save(user);
        admin.setId(user.getId());
    }
}
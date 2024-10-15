package com.bv.pet.jeduler.application.runners;

import com.bv.pet.jeduler.application.cache.AdminInfo;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.bv.pet.jeduler.entities.user.Role.ADMIN;

@Component
@RequiredArgsConstructor
@Order(1)
public class AdminRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final AdminInfo adminInfo;
    private final Environment env;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        createAdmin();
    }

    private void createAdmin() {
        String uuid = env.getProperty("custom.admin.uuid");

        adminInfo.setUuid(uuid);

        Optional<User> admin = userRepository.findByUuid(uuid);
        if (admin.isPresent()){
            adminInfo.setId(admin.get().getId());
            return;
        }

        User user = new User(
                uuid,
                ADMIN
        );

        userRepository.save(user);
        adminInfo.setId(user.getId());
    }
}
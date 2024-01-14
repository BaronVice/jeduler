package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.user.Role;
import com.bv.pet.jeduler.entities.user.User;

import java.util.Random;

public class GenerateUserTask extends GenerateTask<User> {
    @Override
    public void run() {
        Random random = new Random();
        int first = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int second = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        // 123
        String MOCK_USER_PASSWORD =
                "$argon2id$v=19$m=16384,t=2,p=1$ucTBGBGVW2SmbpmGEQlkJw$P61VQNQilS+zeoU9Zyg8joaSkM8nUwHG+HcyJprBDSQ";

        getList().add(
                User.builder()
                        .username(String.format("mock%d@mock%d.com", first, second))
                        .password(MOCK_USER_PASSWORD)
                        .role(Role.USER)
                        .build()
        );
    }
}

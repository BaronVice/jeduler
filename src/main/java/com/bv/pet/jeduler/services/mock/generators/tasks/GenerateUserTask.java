package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.user.Role;
import com.bv.pet.jeduler.entities.user.User;

import java.util.List;


public class GenerateUserTask extends GenerateTask<User> {
    public GenerateUserTask(List<User> list){
        super(list);
    }

    @Override
    public void run() {
        int first = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int second = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        String name = faker.lorem().word();
        // 123
        String MOCK_USER_PASSWORD =
                "$argon2id$v=19$m=16384,t=2,p=1$ucTBGBGVW2SmbpmGEQlkJw$P61VQNQilS+zeoU9Zyg8joaSkM8nUwHG+HcyJprBDSQ";

        list.add(
                User.builder()
                        .username(
                                String.format(
                                        "%s%d@mock%d.com",
                                        name,
                                        first,
                                        second
                                )
                        )
                        .password(MOCK_USER_PASSWORD)
                        .role(Role.USER)
                        .build()
        );
    }
}

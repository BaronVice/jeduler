package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateUserTask;
import org.springframework.stereotype.Component;

@Component
public class UserGenerator extends Generator<User, GenerateUserTask> {
    protected UserGenerator() {
        super(GenerateUserTask.class);
    }
}

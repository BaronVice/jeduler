package com.bv.pet.jeduler.services.populate.generators;

import com.bv.pet.jeduler.entities.user.Role;
import com.bv.pet.jeduler.entities.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class UserGenerator implements Generator<User> {
    @Value("${custom.generated.user.password}")
    private String userPassword;

    @AllArgsConstructor
    private class GenerateUserTask implements Runnable {
        private List<User> list;
        @Override
        public void run() {
            Random random = new Random();
            int first = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            int second = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            list.add(
                    User.builder()
                            .username(String.format("%d@%d.com", first, second))
                            .password(userPassword)
                            .role(Role.USER)
                            .build()
            );
        }
    }

    @Override
    public List<User> generate(int amount) {
        List<User> users = Collections.synchronizedList(new LinkedList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < amount; i++) {
            executorService.submit(() -> new GenerateUserTask(users).run());
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            System.out.println("Aboba");
        }

        return users;
    }
}

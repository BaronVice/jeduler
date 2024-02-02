package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Notification;

import java.time.Instant;
import java.util.List;

public class GenerateNotificationTask extends GenerateTask<Notification> {
    public GenerateNotificationTask(List<Notification> list){
        super(list);
    }

    @Override
    public void run() {
        Instant notifyAt = Instant.now().plusSeconds(random.nextInt(20, 1000));

        list.add(
                Notification.builder()
                        .notifyAt(notifyAt)
                        .build()
        );
    }
}

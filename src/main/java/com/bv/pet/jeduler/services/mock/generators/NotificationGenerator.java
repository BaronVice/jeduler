package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateNotificationTask;
import org.springframework.stereotype.Component;

@Component
public class NotificationGenerator extends Generator<Notification, GenerateNotificationTask> {
    public NotificationGenerator(){
        super(GenerateNotificationTask.class);
    }
}

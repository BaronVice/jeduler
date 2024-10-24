package com.bv.pet.jeduler.services.notificationsenders.decoratorimpl;

import org.springframework.stereotype.Component;

@Component
public class NotificationDecoratorBase implements NotificationSender{
    @Override
    public void send(int taskId, short userId) {
    }
}

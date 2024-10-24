package com.bv.pet.jeduler.services.notificationsenders.decoratorimpl;

import lombok.AllArgsConstructor;

// With firebase message sender but maybe later
@AllArgsConstructor
public abstract class NotificationDecorator implements NotificationSender {
    protected NotificationSender notificationSender;

    @Override
    public void send(int taskId, short userId) {
        notificationSender.send(taskId, userId);
    }
}

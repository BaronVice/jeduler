package com.bv.pet.jeduler.services.notificationsenders;

import lombok.AllArgsConstructor;

// With firebase message sender but maybe later
@AllArgsConstructor
public abstract class NotificationDecorator implements NotificationSender {
    private NotificationSender notificationSender;

    @Override
    public void send(int taskId, short userId) {
        notificationSender.send(taskId, userId);
    }
}

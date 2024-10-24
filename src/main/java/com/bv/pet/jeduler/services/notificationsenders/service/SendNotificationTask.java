package com.bv.pet.jeduler.services.notificationsenders.service;

import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationSender;

public class SendNotificationTask implements Runnable{
    private final NotificationSender sender;
    private final short userId;
    private final int taskId;

    public SendNotificationTask(NotificationSender sender, Short userId, Integer taskId) {
        this.sender = sender;
        this.userId = userId;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        sender.send(taskId, userId);
    }
}

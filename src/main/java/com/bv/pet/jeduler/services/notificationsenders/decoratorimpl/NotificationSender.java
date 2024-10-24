package com.bv.pet.jeduler.services.notificationsenders.decoratorimpl;

// This should be located in completely different place. However, let it be for now
// TODO: move in another place
public interface NotificationSender {
    void send(int taskId, short userId);
}

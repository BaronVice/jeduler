package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Notification;

public record MailServiceNotification (
        Notification notification,
        String mail
){
}

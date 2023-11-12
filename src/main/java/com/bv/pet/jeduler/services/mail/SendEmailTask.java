package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SendEmailTask implements Runnable {
    private Notification notification;
    private MailServiceImpl mailService;

    @Override
    public void run() {
        mailService.sendTextEmail(notification);
    }
}

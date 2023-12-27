package com.bv.pet.jeduler.services.mail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SendEmailTask implements Runnable {
    private MailServiceNotification notification;
    private MailServiceImpl mailService;

    @Override
    public void run() {
        mailService.sendTextEmail(notification);
    }
}

package com.bv.pet.jeduler.services.mail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SendEmailTask implements Runnable {
    private String mail;
    private int id;
    private MailServiceImpl mailService;

    @Override
    public void run() {
        mailService.sendTextEmail(mail, id);
    }
}

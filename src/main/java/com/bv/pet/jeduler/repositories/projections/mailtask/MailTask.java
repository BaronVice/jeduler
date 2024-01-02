package com.bv.pet.jeduler.repositories.projections.mailtask;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public interface MailTask {
    String getUsername();
    Short getId();
    @Value("#{target.notify_at}")
    Instant getNotifyAt();
}

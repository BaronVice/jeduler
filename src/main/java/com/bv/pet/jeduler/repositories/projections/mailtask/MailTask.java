package com.bv.pet.jeduler.repositories.projections.mailtask;

import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

public interface MailTask {
    Integer getId();
    @Value("#{target.user_id}")
    Short getUserId();
    @Value("#{target.notify_at}")
    Instant getNotifyAt();
}

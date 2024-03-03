package com.bv.pet.jeduler.repositories.projections.chat;

import org.springframework.beans.factory.annotation.Value;

public interface UserChat {
    @Value("#{target.user.getId()}")
    Short getUserId();
    Long getId();
}

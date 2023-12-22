package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}

package com.bv.pet.jeduler.repositories;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.repositories.projections.mailtask.MailTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    String GET_MAIL_NOTIFICATION =
            "select t.id, t.user_id, n.notify_at from task t inner join notification n on t.id = n.task_id";

    @Query(value = GET_MAIL_NOTIFICATION, nativeQuery = true)
    List<MailTask> getMailNotifications();
}

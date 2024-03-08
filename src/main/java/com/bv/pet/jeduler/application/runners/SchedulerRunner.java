package com.bv.pet.jeduler.application.runners;

import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.projections.mailtask.MailTask;
import com.bv.pet.jeduler.services.notificationsenders.NotificationService;
import com.bv.pet.jeduler.services.notificationsenders.SendNotificationTask;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class SchedulerRunner implements ApplicationRunner {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public void run(ApplicationArguments args) {
        ThreadPoolTaskScheduler scheduler = notificationService.getScheduler();
        List<MailTask> tasks = notificationRepository.getMailNotifications();

        for (MailTask task : tasks) {
            scheduler.schedule(
                    new SendNotificationTask(
                            notificationService,
                            task.getUserId(),
                            task.getId()
                    ),
                    task.getNotifyAt()
            );
        }
    }
}

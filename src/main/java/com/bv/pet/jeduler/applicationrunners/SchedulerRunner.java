package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.projections.mailtask.MailTask;
import com.bv.pet.jeduler.services.mail.MailServiceImpl;
import com.bv.pet.jeduler.services.mail.SendEmailTask;
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
    private final MailServiceImpl mailService;
    private final NotificationRepository notificationRepository;

    @Override
    public void run(ApplicationArguments args) {
        ThreadPoolTaskScheduler scheduler = mailService.getScheduler();
        List<MailTask> tasks = notificationRepository.getMailNotifications();

        for (MailTask task : tasks) {
            scheduler.schedule(
                    new SendEmailTask(
                            task.getUsername(),
                            task.getId(),
                            mailService
                    ),
                    task.getNotifyAt()
            );
        }
    }
}

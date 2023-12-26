package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

/**
 * Implementation of service used for sending notifications
 */
@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MailServiceImpl {
    private final StatisticsService statisticsService;
    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final ThreadPoolTaskScheduler scheduler;
    private final Map<Integer, Instant> instants;

    @Transactional
    public void sendTextEmail(Notification notification){
        Instant real = instants.get(notification.getId());
        if ( real == null || ( ! real.equals(notification.getNotifyAt()) ) )
            return;

        Task task = taskRepository.findById(notification.getId()).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );

        logger.info("Sending email...");
        SimpleMailMessage message = buildMessage(task);
        javaMailSender.send(message); // Maybe sync to avoid spamming, but I think it's fine
        logger.info("Simple Email sent");

        statisticsService.onSendingNotification();

        removeNotification(task);
    }

    @Async
    public void handNotificationInScheduler(Task task) {
        if (task.getNotification() != null){
            Notification notification = task.getNotification();
            instants.put(
                    task.getId(),
                    notification.getNotifyAt()
            );

            scheduler.schedule(
                    new SendEmailTask(notification, this),
                    notification.getNotifyAt()
            );
        }
    }

    private SimpleMailMessage buildMessage(Task task){
        SimpleMailMessage message = new SimpleMailMessage();
        // Username is email
        message.setTo(task.getUser().getUsername());
        message.setFrom("Jeduler");
        message.setSubject(MessageFormatter.formatSubject(task));
        message.setText(MessageFormatter.formatText(task));

        return message;
    }

    private void removeNotification(Task task){
        task.setNotification(null);
        instants.remove(task.getId());
    }
}

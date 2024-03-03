package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.UserRepository;
import com.bv.pet.jeduler.services.mock.pools.TaskPool;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

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
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    @Qualifier("telegramSender")
    private final ThreadPoolTaskScheduler scheduler;
    private final Map<Integer, ScheduledFuture<?>> futureMap;
    private final TaskPool taskPool;

    @Transactional(readOnly = true)
    public void sendTextEmail(String mail, int taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );

        logger.info("Sending email...");
        SimpleMailMessage message = buildMessage(mail, task);
        javaMailSender.send(message); // Maybe sync to avoid spamming, but I think it's fine
        logger.info("Simple Email sent");

//        statisticsService.onSendingNotification();

        removeNotification(task);
    }

    @Async
    public void handNotificationInScheduler(String mail, Task task) {
        if (task.getNotification() != null){
            Notification notification = task.getNotification();

            ScheduledFuture<?> future = scheduler.schedule(
                    new SendEmailTask(
                            mail,
                            task.getId(),
                            this
                    ),
                    notification.getNotifyAt()
            );

            futureMap.put(
                    task.getId(),
                    future
            );
        }
    }

    @Async
    public void removeNotificationFromScheduler(int id){
        ScheduledFuture<?> future = futureMap.get(id);
        if (future != null)
            future.cancel(true);
    }

    @Transactional(readOnly = true)
    public SimpleMailMessage buildMessage(String mail, Task task){
        SimpleMailMessage message = new SimpleMailMessage();
        // Username is email

        message.setTo(mail);
        message.setFrom("Jeduler");
        message.setSubject(MessageFormatter.formatSubject(task));
        message.setText(
                MessageFormatter.formatText(
                        task,
                        categoryRepository.findNamesByTaskId(task.getId())
                )
        );

        return message;
    }

    private void removeNotification(Task task){
        task.getNotification().setTask(null);
        task.setNotification(null);
        futureMap.remove(task.getId());
        taskRepository.save(task);
        taskPool.checkIn(task);
    }
}

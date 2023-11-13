package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MailServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    private final Map<Long, Instant> instants;
    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;
    @Value("${custom.mail}")
    private String sendTo;

    // @Async ??
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

        removeNotification(task);
    }

    private SimpleMailMessage buildMessage(Task task){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
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

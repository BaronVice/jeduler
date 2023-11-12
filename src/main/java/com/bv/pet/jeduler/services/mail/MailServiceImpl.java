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

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class MailServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    @Value("${custom.mail}")
    private String sendTo;
    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;

    // @Async ??
    @Transactional
    public void sendTextEmail(Notification notification){
        Task task = taskRepository.findById(notification.getId()).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );

        logger.info("Sending email...");
        SimpleMailMessage message = buildMessage(task);
        javaMailSender.send(message);
        logger.info("Simple Email sent");

        task.setNotification(null);
    }

    private SimpleMailMessage buildMessage(Task task){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setFrom("Jeduler");
        message.setSubject(MessageFormatter.formatSubject(task));
        message.setText(MessageFormatter.formatText(task));

        return message;
    }
}

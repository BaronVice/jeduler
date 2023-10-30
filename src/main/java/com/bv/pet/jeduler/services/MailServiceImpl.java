package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
    private final TreeSet<Notification> notifications = new TreeSet<>();

    @Scheduled(fixedRate = 3000)
    @Transactional
    public void sendTextEmail(){
        if (notifications.isEmpty() || notifications.first().getNotifyAt().isAfter(Instant.now())){
            logger.info("Sleep...");
            return;
        }

        Notification notification = notifications.pollFirst();
        Task task = taskRepository.findById(notification.getId()).get(); // it's fine

        StringBuilder stringBuilder = new StringBuilder();

        logger.info("Sending email...");
        buildMessage(stringBuilder, task);

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(sendTo);
        simpleMessage.setSubject("Jeduler task: " + task.getName());
        simpleMessage.setText(
                stringBuilder.toString()
        );

        javaMailSender.send(simpleMessage);

        logger.info("Simple Email sent");
        notifications.remove(notification);
        notificationRepository.deleteById(notification.getId());
    }

    private void buildMessage(StringBuilder stringBuilder, Task task){
        Date startDate = Date.from(task.getStartsAt());
        Date expiresDate = Date.from(task.getExpiresAt());

        stringBuilder
                .append("Notifying that you have a task named ").append(task.getName()).append("\n");
        if (task.getDescription() != null){
            stringBuilder.append("Description: ").append(task.getDescription()).append("\n\n");
        }
        stringBuilder
                .append("Starts at: ").append(startDate).append("\n")
                .append("Expires at: ").append(expiresDate).append("\n\n");
        if(task.getCategories() != null){
            stringBuilder
                    .append("Categories: ").append(
                            task.getCategories().stream().map(Category::getName)
                                    .collect(Collectors.joining(", "))
                    );
        }
    }
}

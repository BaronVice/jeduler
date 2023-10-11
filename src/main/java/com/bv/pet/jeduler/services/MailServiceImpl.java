package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;

    public void sendTextEmail(Task task){
        StringBuilder stringBuilder = new StringBuilder();

        logger.info("Sending email...");
        buildMessage(stringBuilder, task);

        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo("toni.sas.228@mail.ru");
        simpleMessage.setSubject("Jeduler task: " + task.getName());
        simpleMessage.setText(
                stringBuilder.toString()
        );

        javaMailSender.send(simpleMessage);

        logger.info("Simple Email sent");
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

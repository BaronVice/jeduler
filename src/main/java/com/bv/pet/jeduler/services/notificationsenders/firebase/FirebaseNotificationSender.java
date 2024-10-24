package com.bv.pet.jeduler.services.notificationsenders.firebase;


import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.RestExceptionHandler;
import com.bv.pet.jeduler.repositories.UserRepository;
import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationDecorator;
import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationSender;
import com.bv.pet.jeduler.services.notificationsenders.service.NotificationMessageBuilder;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FirebaseNotificationSender extends NotificationDecorator {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseNotificationSender.class);
    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final NotificationMessageBuilder messageBuilder;

    @Autowired
    public FirebaseNotificationSender(
            @Qualifier("notificationDecoratorBase") NotificationSender notificationSender,
            UserRepository userRepository,
            FirebaseMessaging firebaseMessaging,
            NotificationMessageBuilder messageBuilder
    ) {
        super(notificationSender);
        this.userRepository = userRepository;
        this.firebaseMessaging = firebaseMessaging;
        this.messageBuilder = messageBuilder;
    }

    @Override
    public void send(int taskId, short userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            logger.info("Failed to find user with id = " + userId);
            return;
        }

        String uuid = user.get().getUuid();
        Message message = Message.builder()
                .setToken(uuid)
                .putData("body", messageBuilder.buildStringMessage(taskId))
                .build();
        try{
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e){
            logger.info(
                    String.format(
                            "Failed to send firebase message: taskId = %d; userId = %d; uuid = %s; %s",
                            taskId, userId, uuid, e.getMessage()
                    )
            );
        }

        notificationSender.send(taskId, userId);
    }
}

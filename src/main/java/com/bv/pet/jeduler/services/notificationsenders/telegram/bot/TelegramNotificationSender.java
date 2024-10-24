package com.bv.pet.jeduler.services.notificationsenders.telegram.bot;

import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationDecorator;
import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationSender;
import com.bv.pet.jeduler.services.notificationsenders.service.NotificationMessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class TelegramNotificationSender extends NotificationDecorator {
    private static final Logger logger = LoggerFactory.getLogger(TelegramNotificationSender.class);
    private final SilentSender silentSender;
    private final Map<Short, Long> usersChatId;
    private final Map<Long, UserState> chatStates;
    private final NotificationMessageBuilder messageBuilder;

    public TelegramNotificationSender(
            NotificationSender sender,
            SilentSender silentSender,
            Map<Short, Long> usersChatId,
            DBContext db,
            NotificationMessageBuilder messageBuilder
    ) {
        super(sender);

        this.silentSender = silentSender;
        this.messageBuilder = messageBuilder;
        this.chatStates = db.getMap(Constants.CHAT_STATES);
        this.usersChatId = usersChatId;
    }

    @Override
    public void send(int taskId, short userId) {
        Long chatId = usersChatId.get(userId);
        if (chatId != null && chatStates.get(chatId) == UserState.CONNECTED){
            SendMessage m = new SendMessage();
            m.setChatId(chatId);
            m.setText(formText(taskId));
            silentSender.execute(m);
            logger.info("Notification is sent to chatId = " + chatId);
        } else {
            logger.info("Notification ignored: chatId = " + chatId + " is not connected in chatStates");
        }

        notificationSender.send(taskId, userId);
    }

    private String formText(Integer taskId){
        return messageBuilder.buildStringMessage(taskId);
    }
}

package com.bv.pet.jeduler.services.notificationsenders.telegram.bot;

import com.bv.pet.jeduler.services.notificationsenders.NotificationMessageBuilder;
import com.bv.pet.jeduler.services.notificationsenders.NotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class TelegramNotificationSender implements NotificationSender {
    private static final Logger logger = LoggerFactory.getLogger(TelegramNotificationSender.class);
    private final SilentSender sender;
    private final Map<Short, Long> usersChatId;
    private final Map<Long, UserState> chatStates;
    private final NotificationMessageBuilder messageBuilder;

    public TelegramNotificationSender(SilentSender sender, Map<Short, Long> usersChatId, DBContext db, NotificationMessageBuilder messageBuilder) {
        this.sender = sender;
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
            sender.execute(m);
            logger.info("Notification is sent to chatId = " + chatId);
        } else {
            logger.info("Notification ignored: chatId = " + chatId + " is not connected in chatStates");
        }
    }

    private String formText(Integer taskId){
        return messageBuilder.buildStringMessage(taskId);
    }
}

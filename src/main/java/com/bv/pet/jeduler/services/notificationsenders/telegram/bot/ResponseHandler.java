package com.bv.pet.jeduler.services.notificationsenders.telegram.bot;

import com.bv.pet.jeduler.services.notificationsenders.telegram.token.TokenConnector;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Map;
import java.util.Objects;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, UserState> chatStates;
    private final TokenConnector connector;

    public ResponseHandler(SilentSender sender, DBContext db, TokenConnector connector) {
        this.sender = sender;
        this.connector = connector;
        chatStates = db.getMap(Constants.CHAT_STATES);
    }

    public void replyToStart(long chatId) {
        if (connector.isConnected(chatId)){
            changeToConnectedState(chatId);
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(Constants.START_TEXT);
        sender.execute(message);
        chatStates.put(chatId, UserState.AWAITING_TOKEN);
    }

    public void replyToButtons(long chatId, Message message) {
        System.out.println(chatId);
        if (message.getText() == null)
            return;

        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
        }

        if (Objects.requireNonNull(chatStates.get(chatId)) == UserState.AWAITING_TOKEN) {
            replyToToken(chatId, message);
        }
    }

    private void replyToToken(long chatId, Message token) {
        if (! connector.connect(chatId, token.getText()))
            return;

        changeToConnectedState(chatId);
    }

    private void changeToConnectedState(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(Constants.CONNECTED_TEXT);
        sender.execute(message);
        chatStates.put(chatId, UserState.CONNECTED);
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Alright. No notifications for you, unless you /start again");
        chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }
}

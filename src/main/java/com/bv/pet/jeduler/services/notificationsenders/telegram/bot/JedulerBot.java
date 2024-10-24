package com.bv.pet.jeduler.services.notificationsenders.telegram.bot;

import com.bv.pet.jeduler.application.cache.TelegramInfo;
import com.bv.pet.jeduler.services.notificationsenders.telegram.token.TokenConnector;
import com.bv.pet.jeduler.services.notificationsenders.service.NotificationMessageBuilder;
import com.bv.pet.jeduler.services.notificationsenders.decoratorimpl.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
// https://web.telegram.org/k/#@JedulerBot
public class JedulerBot extends AbilityBot implements NotificationSender {
    private final ResponseHandler responseHandler;
    private final TelegramNotificationSender tgSender;

    @Autowired
    public JedulerBot(
            @Qualifier("firebaseNotificationSender") NotificationSender sender,
            Environment env,
            TokenConnector connector,
            NotificationMessageBuilder messageBuilder,
            TelegramInfo telegramInfo
    ){
        super(
                env.getProperty("custom.bot.token"),
                env.getProperty("custom.bot.name")
        );

        responseHandler = new ResponseHandler(silent, db, connector);
        tgSender = new TelegramNotificationSender(
                sender,
                silent,
                telegramInfo.getUsersChatId(),
                db,
                messageBuilder
        );
    }

    @Override
    public long creatorId() {
        return 565341892L;
    }

    public Ability startBot(){
        return Ability
                .builder()
                .name("start")
                .info(Constants.START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) -> responseHandler.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT, upd -> responseHandler.userIsActive(getChatId(upd)));
    }

    @Override
    public void send(int taskId, short userId) {
        tgSender.send(taskId, userId);
    }
}

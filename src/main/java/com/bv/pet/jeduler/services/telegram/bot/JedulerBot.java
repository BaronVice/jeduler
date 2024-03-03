package com.bv.pet.jeduler.services.telegram.bot;

import com.bv.pet.jeduler.services.telegram.token.TokenConnector;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JedulerBot extends AbilityBot {
    private final ResponseHandler responseHandler;
    // TODO: private final NotificationSender sender; ?

    @Autowired
    public JedulerBot(Environment env, TokenConnector connector){
        super(
                env.getProperty("custom.bot.token"),
                env.getProperty("custom.bot.name")
        );

        responseHandler = new ResponseHandler(silent, db, connector);
    }

    @Override
    public long creatorId() {
        return 1L;
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
}

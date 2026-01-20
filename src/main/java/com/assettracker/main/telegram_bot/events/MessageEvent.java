package com.assettracker.main.telegram_bot.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageEvent extends ApplicationEvent {

    @Getter
    private final Messages message;
    @Getter
    private final Update update;

    public MessageEvent(Object source, Messages message, Update update) {
        super(source);
        this.message = message;
        this.update = update;
    }
}

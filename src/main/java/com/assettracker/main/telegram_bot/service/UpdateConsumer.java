package com.assettracker.main.telegram_bot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final MessageHandler messageHandler;
    private final ButtonHandler buttonHandler;

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandler.handleAnyMessage(update);
        } else if (update.hasCallbackQuery()) {
            buttonHandler.handle(update.getCallbackQuery());
        }
    }
}

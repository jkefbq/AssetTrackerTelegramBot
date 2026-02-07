package com.cryptodemoaccount.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@AllArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final ExecutorService es = Executors.newCachedThreadPool();
    private final MessageHandler messageHandler;
    private final ButtonHandler buttonHandler;

    @Override
    public void consume(Update update) {
        es.execute(() -> {
            if (update.hasMessage() && update.getMessage().hasText()) {
                messageHandler.handle(update);
            } else if (update.hasCallbackQuery()) {
                buttonHandler.handle(update.getCallbackQuery());
            }
        });
    }
}

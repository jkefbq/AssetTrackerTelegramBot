package com.cryptodemoaccount.config;

import com.cryptodemoaccount.service.UpdateConsumer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
public class TelegramBot implements SpringLongPollingBot {

    private final String TG_TOKEN;
    private final UpdateConsumer updateConsumer;

    public TelegramBot(YamlConfig config, UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
        this.TG_TOKEN = config.getApi().getTelegram().getKey();
    }

    @Override
    public String getBotToken() {
        return TG_TOKEN;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}

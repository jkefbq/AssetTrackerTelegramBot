package com.assettracker.main.telegram_bot.events;

import lombok.Getter;

import java.util.Arrays;

public enum Messages {
    UNKNOWN(""),
    START("/start"),
    MENU("/menu"),
    BAG("/bag");

    @Getter
    private final String text;

    Messages(String text) {
        this.text = text;
    }

    public static Messages parseText(String text) {
        return Arrays.stream(Messages.values())
                .filter(msg -> msg.getText().equals(text))
                .findFirst()
                .orElseThrow();
    }
}

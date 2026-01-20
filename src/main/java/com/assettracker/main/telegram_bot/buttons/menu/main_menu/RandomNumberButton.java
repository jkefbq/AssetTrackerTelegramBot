package com.assettracker.main.telegram_bot.buttons.menu.main_menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class RandomNumberButton implements IMainMenuButton {

    @Getter
    private final String callbackData = "randomNumber";

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Случайное число")
                .callbackData(callbackData)
                .build();
    }
}
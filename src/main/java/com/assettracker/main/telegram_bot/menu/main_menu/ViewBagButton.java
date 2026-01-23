package com.assettracker.main.telegram_bot.menu.main_menu;

import com.assettracker.main.telegram_bot.events.Buttons;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class ViewBagButton implements IMainMenuButton {

    @Getter
    private final String callbackData = Buttons.MY_BAG.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Мой портфель")
                .callbackData(callbackData)
                .build();
    }
}

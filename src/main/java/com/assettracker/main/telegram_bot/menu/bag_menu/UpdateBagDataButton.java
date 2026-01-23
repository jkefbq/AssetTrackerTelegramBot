package com.assettracker.main.telegram_bot.menu.bag_menu;

import com.assettracker.main.telegram_bot.events.Buttons;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class UpdateBagDataButton implements IBagMenuButton {

    @Getter
    private final String callbackData = Buttons.UPDATE_BAG_DATA.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Обновить")
                .callbackData(callbackData)
                .build();
    }
}

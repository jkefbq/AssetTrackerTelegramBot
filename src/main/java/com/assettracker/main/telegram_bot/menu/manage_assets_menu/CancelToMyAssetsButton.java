package com.assettracker.main.telegram_bot.menu.manage_assets_menu;

import com.assettracker.main.telegram_bot.events.Buttons;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class CancelToMyAssetsButton implements IManageAssetsMenuButton {

    @Getter
    private final String callbackData = Buttons.CANCEL_TO_MY_ASSETS.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(callbackData)
                .build();
    }
}

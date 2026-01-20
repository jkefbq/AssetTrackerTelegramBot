package com.assettracker.main.telegram_bot.buttons.menu.bag_menu;

import com.assettracker.main.telegram_bot.events.Buttons;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class ManageAssetsButton implements IBagMenuButton {

    @Getter
    private final String callbackData = Buttons.MANAGE_ASSETS.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Управлять активами")
                .callbackData(callbackData)
                .build();
    }

}

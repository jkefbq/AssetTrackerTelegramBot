package com.cryptodemoaccount.menu.assets_menu;

import com.cryptodemoaccount.event.Button;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class MyAssetsButton implements IAssetsMenuButton {

    @Getter
    private final String callbackData = Button.MY_ASSETS.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("📃 Мои активы")
                .callbackData(callbackData)
                .build();
    }
}

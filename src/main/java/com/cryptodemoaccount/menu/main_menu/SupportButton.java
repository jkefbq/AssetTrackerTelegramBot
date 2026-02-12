package com.cryptodemoaccount.menu.main_menu;

import com.cryptodemoaccount.event.Button;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class SupportButton implements IMainMenuButton {

    @Getter
    private final String callbackData = Button.SUPPORT.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("🦺 Поддержка")
                .callbackData(callbackData)
                .build();
    }
}

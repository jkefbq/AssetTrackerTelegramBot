package com.cryptodemoaccount.menu.assets_menu;

import com.cryptodemoaccount.event.Button;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class CancelToBagMenuButton implements IAssetsMenuButton {

    @Getter
    private final String callbackData = Button.CANCEL_TO_BAG_MENU.getCallbackData();


    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("Назад")
                .callbackData(callbackData)
                .build();
    }
}

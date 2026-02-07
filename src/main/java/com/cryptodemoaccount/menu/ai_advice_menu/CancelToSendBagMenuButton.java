package com.cryptodemoaccount.menu.ai_advice_menu;

import com.cryptodemoaccount.events.Button;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class CancelToSendBagMenuButton implements IAiAdviceMenuButton {

    @Getter
    private final String callbackData = Button.CANCEL_TO_SEND_BAG_MENU.getCallbackData();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("🔄 В портфель")
                .callbackData(callbackData)
                .build();
    }
}

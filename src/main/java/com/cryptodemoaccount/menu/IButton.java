package com.cryptodemoaccount.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface IButton {
    InlineKeyboardButton getButton();
    String getCallbackData();
}

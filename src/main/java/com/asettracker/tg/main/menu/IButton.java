package com.asettracker.tg.main.menu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface IButton extends CanHandleButton {
    InlineKeyboardButton getButton();
}

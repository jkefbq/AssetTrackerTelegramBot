package com.asettracker.tg.main.menu;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CanHandleButton {
    boolean canHandleButton(CallbackQuery callbackQuery);
    void handleButton(CallbackQuery callbackQuery);
}

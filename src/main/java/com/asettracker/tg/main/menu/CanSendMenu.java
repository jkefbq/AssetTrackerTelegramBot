package com.asettracker.tg.main.menu;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CanSendMenu {
    void sendMenu(Update update);
}

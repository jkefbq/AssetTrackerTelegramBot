package com.asettracker.tg.main.service;

import com.asettracker.tg.main.menu.CanHandleButton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Service
@AllArgsConstructor
public class GeneralButtonHandler {
    private List<CanHandleButton> allHandlers;

    public void handleAnyButton(CallbackQuery callbackQuery) {
        allHandlers.forEach(handler -> {
            if (handler.canHandleButton(callbackQuery)) {
                handler.handleButton(callbackQuery);
            }
        });
    }
}

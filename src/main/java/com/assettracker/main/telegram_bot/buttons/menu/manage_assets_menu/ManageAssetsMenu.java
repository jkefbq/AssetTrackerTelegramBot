package com.assettracker.main.telegram_bot.buttons.menu.manage_assets_menu;

import com.assettracker.main.telegram_bot.buttons.menu.IMenu;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
@AllArgsConstructor
public class ManageAssetsMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final List<IManageAssetsMenuButton> buttons;
    private final LastMessageService lastMessageService;

    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Выберите действие:")
                .replyMarkup(combineButtons(buttons))
                .build();
        telegramClient.execute(editMessageText);
        lastMessageService.setLastMessage(chatId, editMessageText.getMessageId());
    }

    @SneakyThrows
    @Override
    public void sendMenu(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Выберите действие:")
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }
}

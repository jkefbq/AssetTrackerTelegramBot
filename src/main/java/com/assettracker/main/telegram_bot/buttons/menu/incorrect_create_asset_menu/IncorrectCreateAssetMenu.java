package com.assettracker.main.telegram_bot.buttons.menu.incorrect_create_asset_menu;

import com.assettracker.main.telegram_bot.buttons.menu.IButton;
import com.assettracker.main.telegram_bot.buttons.menu.IMenu;
import com.assettracker.main.telegram_bot.buttons.menu.incorrect_update_asset_menu.CancelToManageAssets;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
@AllArgsConstructor
public class IncorrectCreateAssetMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final List<IIncorrectCreateAssetMenuButton> buttons;
    private final LastMessageService lastMessageService;
    private final ForceUpdateAssetButton updateButton;
    private final CancelToManageAssets cancelButton;

    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("У вас уже есть данная монета, хотите обновить ее?")
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
                .text("У вас уже есть данная монета, хотите обновить ее?")
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    @Override
    public InlineKeyboardMarkup combineButtons(List<? extends IButton> buttons) {
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(updateButton.getButton()),
                        new InlineKeyboardRow(cancelButton.getButton())
                )
        );
    }
}

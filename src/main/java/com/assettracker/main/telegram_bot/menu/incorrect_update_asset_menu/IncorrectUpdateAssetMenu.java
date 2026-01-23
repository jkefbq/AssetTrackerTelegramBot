package com.assettracker.main.telegram_bot.menu.incorrect_update_asset_menu;

import com.assettracker.main.telegram_bot.menu.IButton;
import com.assettracker.main.telegram_bot.menu.IMenu;
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
public class IncorrectUpdateAssetMenu implements IMenu {

    private List<IIncorrectUpdateAssetMenuButton> buttons;
    private TelegramClient telegramClient;
    private LastMessageService lastMessageService;
    private ForceCreateAssetButton createButton;
    private CancelToManageAssets cancelButton;

    @Override
    @SneakyThrows
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("У вас нет этой монеты, хотите ее добавить?")
                .replyMarkup(combineButtons(buttons))
                .build();
        telegramClient.execute(editMessageText);
        lastMessageService.setLastMessage(chatId, editMessageText.getMessageId());
    }

    @Override
    @SneakyThrows
    public void sendMenu(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("У вас нет этой монеты, хотите ее добавить?")
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    @Override
    public InlineKeyboardMarkup combineButtons(List<? extends IButton> buttons) {
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(createButton.getButton()),
                        new InlineKeyboardRow(cancelButton.getButton())
                )
        );
    }
}

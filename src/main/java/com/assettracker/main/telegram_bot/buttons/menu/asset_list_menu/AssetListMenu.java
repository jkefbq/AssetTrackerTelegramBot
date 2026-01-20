package com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu;

import com.assettracker.main.telegram_bot.buttons.menu.IButton;
import com.assettracker.main.telegram_bot.buttons.menu.IMenu;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
@AllArgsConstructor
public class AssetListMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final LastMessageService lastMessageService;
    private final List<IAssetListMenuButton> buttons;

    @Override
    @SneakyThrows
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Выберите монету:")
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
                .text("Выберите монету:")
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());//todo настроить фулл очистку таблицы user_coin при старте проги
    }

    @Override
    public InlineKeyboardMarkup combineButtons(List<? extends IButton> buttons) {
        List<InlineKeyboardButton> but = buttons.stream().map(IButton::getButton).toList();
        return new InlineKeyboardMarkup(
                List.of(
                        new InlineKeyboardRow(but.get(0), but.get(1)),
                        new InlineKeyboardRow(but.get(2), but.get(3)),
                        new InlineKeyboardRow(but.get(4), but.get(5)),
                        new InlineKeyboardRow(but.get(6), but.get(7)),
                        new InlineKeyboardRow(but.get(8), but.get(9))
                )
        );
    }
}

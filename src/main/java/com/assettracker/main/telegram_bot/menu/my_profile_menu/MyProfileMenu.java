package com.assettracker.main.telegram_bot.menu.my_profile_menu;

import com.assettracker.main.telegram_bot.menu.IMenu;
import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.dto.UserDto;
import com.assettracker.main.telegram_bot.database.service.BagService;
import com.assettracker.main.telegram_bot.database.service.UserService;
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
public class MyProfileMenu implements IMenu {

    private final List<IMyProfileMenuButton> buttons;
    private final UserService userService;
    private final BagService bagService;
    private final TelegramClient telegramClient;
    private final LastMessageService lastMessageService;

    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        UserDto user = userService.findByChatId(chatId).orElseThrow();
        BagDto bag = bagService.findByChatId(chatId).orElseThrow();
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(String.format(getMenuText(),
                        user.getFirstName(), user.getLastName(),
                        user.getUserName(), bag.getCreatedAt()))
                .replyMarkup(combineButtons(buttons))
                .build();
        telegramClient.execute(editMessageText);
        lastMessageService.setLastMessage(chatId, editMessageText.getMessageId());
    }

    @SneakyThrows
    @Override
    public void sendMenu(Long chatId) {
        UserDto user = userService.findByChatId(chatId).orElseThrow();
        BagDto bag = bagService.findByChatId(chatId).orElseThrow();
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(getMenuText(),
                        user.getFirstName(), user.getLastName(),
                        user.getUserName(), bag.getCreatedAt()))
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    public static String getMenuText() {
        return """
                👤Ваш Профиль:
                ├ Имя: %s
                ├ Фамилия: %s
                ├ Юзернейм: @%s
                
                🕔Торгуете с:
                └ %s""";
    }
}

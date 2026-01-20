package com.assettracker.main.telegram_bot.buttons.menu.bag_menu;

import com.assettracker.main.telegram_bot.buttons.menu.IMenu;
import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.service.BagService;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.File;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BagMenu implements IMenu {

    private final TelegramClient telegramClient;
    private final List<IBagMenuButton> buttons;
    private final BagService bagService;
    private final LastMessageService lastMessageService;

    public static String getMenuText() {
        return """
                \uD83C\uDF92Информация о портфеле:
                ├ Создан: %s
                ├ Количество активов: %s
                
                \uD83D\uDCB0Суммарная стоимость:
                └ %s$""";
    }

    public static InputMediaPhoto getMenuPhoto(BagDto bag) {
        return InputMediaPhoto.builder()
                .media(new File("src/main/resources/static/your-bag.jpg"), "your-bag.jpg")
                .caption(String.format(getMenuText(), bag.getCreatedAt(),
                        bag.getAssetCount(), bag.getTotalCost().setScale(5, RoundingMode.HALF_UP)))
                .build();
    }

    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        BagDto bag = bagService.findByChatId(chatId).orElseThrow();
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(String.format(getMenuText(), bag.getCreatedAt(),
                        bag.getAssetCount(), bag.getTotalCost().setScale(5, RoundingMode.HALF_UP)))
                .replyMarkup(combineButtons(buttons))
                .build();
        telegramClient.execute(editMessageText);
        lastMessageService.setLastMessage(chatId, editMessageText.getMessageId());
    }

    @SneakyThrows
    @Override
    public void sendMenu(Long chatId) {
        BagDto bag = bagService.findByChatId(chatId).orElseThrow();
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(String.format(getMenuText(), bag.getCreatedAt(),
                        bag.getAssetCount(), bag.getTotalCost().setScale(5, RoundingMode.HALF_UP)))
                .replyMarkup(combineButtons(buttons))
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }
}

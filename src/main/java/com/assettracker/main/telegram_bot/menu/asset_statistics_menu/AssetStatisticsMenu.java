package com.assettracker.main.telegram_bot.menu.asset_statistics_menu;

import com.assettracker.main.telegram_bot.database.service.BagService;
import com.assettracker.main.telegram_bot.menu.IMenu;
import com.assettracker.main.telegram_bot.menu.asset_list_menu.Coins;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
@AllArgsConstructor
public class AssetStatisticsMenu implements IMenu {

    private final BagService bagService;
    private final TelegramClient telegramClient;
    private final LastMessageService lastMessageService;

    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        Map<Coins, Map.Entry<BigDecimal, BigDecimal>> changes = bagService.getCoinChanges(chatId);
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(generateText(changes))
                .parseMode(ParseMode.MARKDOWNV2)
                .build();
        telegramClient.execute(editMessageText);
        lastMessageService.setLastMessage(chatId, editMessageText.getMessageId());
    }

    @SneakyThrows
    @Override
    public void sendMenu(Long chatId) {
        Map<Coins, Map.Entry<BigDecimal, BigDecimal>> changes = bagService.getCoinChanges(chatId);
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(generateText(changes))
                .parseMode(ParseMode.MARKDOWNV2)
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    private String generateText(Map<Coins, Map.Entry<BigDecimal, BigDecimal>> changes) {
        StringBuilder result = new StringBuilder("```Статистика\u00A0за\u00A0последние\u00A024\u00A0часа:" +
                "   монета     изменения   цена(1шт)\n---------------------------------------\n");
        changes.forEach((coin, entry) -> {
            if (entry.getKey().compareTo(BigDecimal.ZERO) > 0) {
                result.append(String.format("%-12s : %s%n", "🟢 " + coin.name(), "+" +
                        entry.getKey().setScale(3, RoundingMode.HALF_UP) + "%   " +
                        entry.getValue().setScale(5, RoundingMode.HALF_UP) + "$"));
            } else {
                result.append(String.format("%-12s : %s%n", "🔴 " + coin.name(),
                        entry.getKey().setScale(3, RoundingMode.HALF_UP) + "%   " +
                        entry.getValue().setScale(5, RoundingMode.HALF_UP) + "$"));
            }
        });
        return result.append("```").toString();
    }
}

package com.cryptodemoaccount.menu.ai_advice_menu;

import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.menu.IMenu;
import com.cryptodemoaccount.service.AIService;
import com.cryptodemoaccount.service.LastMessageService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
@AllArgsConstructor
public class AIAdviceMenu implements IMenu {

    private final LastMessageService lastMessageService;
    private final TelegramClient telegramClient;
    private final AIService aiService;
    private final BagService bagService;
    private final CancelToSendBagMenuButton cancelButton;

    @RateLimiter(name = "ai-advice")
    @SneakyThrows
    @Override
    public void editMsgAndSendMenu(Long chatId, Integer messageId) {
        var assets = bagService.findByChatId(chatId).orElseThrow().getAssets();
        String advice = aiService.getAdvice(assets);
        EditMessageText editMessageText = EditMessageText.builder()
                .text(advice)
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(combineButtons(List.of(cancelButton)))
                .parseMode(ParseMode.HTML)
                .build();
        telegramClient.execute(editMessageText);
    }

    @RateLimiter(name = "ai-advice")
    @SneakyThrows
    @Override
    public void sendMenu(Long chatId) {
        var assets = bagService.findByChatId(chatId).orElseThrow().getAssets();
        String advice = aiService.getAdvice(assets);
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(advice)
                .replyMarkup(combineButtons(List.of(cancelButton)))
                .parseMode(ParseMode.HTML)
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }

    @SneakyThrows
    public void sendToManyRequests(Long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text("Упс, кажется вы слишком часто спрашивали помощника, попробуйте позже.")
                .build();
        Message msg = telegramClient.execute(sendMessage);
        lastMessageService.setLastMessage(chatId, msg.getMessageId());
    }
}

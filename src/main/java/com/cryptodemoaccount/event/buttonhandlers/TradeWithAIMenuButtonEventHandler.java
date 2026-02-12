package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.ai_advice_menu.AIAdviceMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeWithAIMenuButtonEventHandler {

    private final LastMessageService lastMessageService;
    private final WaitingMenu waitingMenu;
    private final AIAdviceMenu aiAdviceMenu;

    @EventListener(condition = "event.getButton().name() == 'AI_ADVICE'")
    public void handleAIAdvice(ButtonEvent event) {
        waitingMenu.sendMenu(event.getChatId());
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        try {
            aiAdviceMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } catch (RequestNotPermitted e) {
            aiAdviceMenu.sendToManyRequests(event.getChatId());
        }
    }
}

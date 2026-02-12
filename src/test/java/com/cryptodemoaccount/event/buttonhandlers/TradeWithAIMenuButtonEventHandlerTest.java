package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.ai_advice_menu.AIAdviceMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TradeWithAIMenuButtonEventHandlerTest {

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private WaitingMenu waitingMenu;
    @Mock
    private AIAdviceMenu aiAdviceMenu;

    @InjectMocks
    private TradeWithAIMenuButtonEventHandler tradeWithAIButtonHandler;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Test
    public void handleAIAdviceTest_notExceedingRateLimiter() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.AI_ADVICE, chatId);

        tradeWithAIButtonHandler.handleAIAdvice(event);

        verify(aiAdviceMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleAIAdviceTest_exceedingRateLimiter() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.AI_ADVICE, chatId);
        
        doThrow(RequestNotPermitted.class).when(aiAdviceMenu).editMsgAndSendMenu(eq(chatId), any());

        tradeWithAIButtonHandler.handleAIAdvice(event);

        verify(aiAdviceMenu).sendToManyRequests(chatId);
    }
}

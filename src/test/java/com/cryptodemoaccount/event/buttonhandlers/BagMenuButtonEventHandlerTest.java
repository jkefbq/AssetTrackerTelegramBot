package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.assets_menu.AssetsMenu;
import com.cryptodemoaccount.menu.trade_with_ai_menu.TradeWithAIMenu;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BagMenuButtonEventHandlerTest {

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private AssetsMenu assetsMenu;
    @Mock
    private TradeWithAIMenu tradeWithAIMenu;

    @InjectMocks
    private BagMenuButtonEventHandler bagMenuButtonHandler;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Test
    public void handleAssetsTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.ASSETS, chatId);

        bagMenuButtonHandler.handleAssets(event);

        verify(assetsMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleTradeWithAITest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.TRADE_WITH_AI, chatId);

        bagMenuButtonHandler.handleTradeWithAI(event);

        verify(tradeWithAIMenu).editMsgAndSendMenu(eq(chatId), any());
    }
}

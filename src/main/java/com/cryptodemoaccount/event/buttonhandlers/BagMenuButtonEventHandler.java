package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.assets_menu.AssetsMenu;
import com.cryptodemoaccount.menu.trade_with_ai_menu.TradeWithAIMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BagMenuButtonEventHandler {

    private final LastMessageService lastMessageService;
    private final AssetsMenu assetsMenu;
    private final TradeWithAIMenu tradeWithAIMenu;

    @EventListener(condition = "event.getButton().name() == 'ASSETS'")
    public void handleAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'TRADE_WITH_AI'")
    public void handleTradeWithAI(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        tradeWithAIMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }
}

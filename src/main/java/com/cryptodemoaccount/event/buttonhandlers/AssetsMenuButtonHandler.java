package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_statistics_menu.AssetStatisticsMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssetsMenuButtonHandler {

    private final LastMessageService lastMessageService;
    private final MyAssetsMenu myAssetsMenu;
    private final WaitingMenu waitingMenu;
    private final AssetStatisticsMenu assetStatisticsMenu;

    @EventListener(condition = "event.getButton().name() == 'MY_ASSETS'")
    public void handleMyAssets(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        myAssetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'ASSET_STATISTICS'")
    public void handleAssetStatistics(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetStatisticsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }
}

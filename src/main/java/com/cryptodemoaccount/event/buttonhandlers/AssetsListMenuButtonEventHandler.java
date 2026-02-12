package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.event.AssetButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.menu.incorrect_create_asset_menu.IncorrectCreateAssetMenu;
import com.cryptodemoaccount.menu.incorrect_delete_menu.IncorrectDeleteMenu;
import com.cryptodemoaccount.menu.incorrect_update_asset_menu.IncorrectUpdateAssetMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssetsListMenuButtonEventHandler {

    private final LastMessageService lastMessageService;
    private final MyAssetsMenu myAssetsMenu;
    private final BagService bagService;
    private final UserCoinService userCoinService;
    private final IncorrectUpdateAssetMenu incorrectUpdateAssetMenu;
    private final IncorrectCreateAssetMenu incorrectCreateAssetMenu;
    private final EnterAssetCountMenu enterAssetCountMenu;
    private final IncorrectDeleteMenu incorrectDeleteMenu;
    private final WaitingMenu waitingMenu;

    @EventListener
    public void handleAnyAssetButton(AssetButtonEvent event) {
        var tmpCoin = userCoinService.findByChatId(event.getChatId()).orElseThrow();
        var lastMessageId = lastMessageService.getLastMessage(event.getChatId());

        tmpCoin.setCoin(event.getCoin());
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        userCoinService.create(tmpCoin);
        processAsset(event, tmpCoin, lastMessageId);
    }

    public void processAsset(AssetButtonEvent event, UserCoinDto tmpCoin, Integer lastMessageId) {
        boolean hasCoin = bagService.hasCoin(event.getChatId(), event.getCoin());

        if (tmpCoin.getAssetDo() == AssetDo.CREATE && hasCoin) {
            incorrectCreateAssetMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.UPDATE && !hasCoin) {
            incorrectUpdateAssetMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.DELETE && hasCoin) {
            deleteAssetAndSendSuccess(event, tmpCoin, lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.DELETE && !hasCoin) {
            incorrectDeleteMenu.editMsgAndSendMenu(tmpCoin.getChatId(), lastMessageId);
        } else {
            enterAssetCountMenu.editMsgAndSendMenu(tmpCoin.getChatId(), lastMessageId);
        }
    }

    public void deleteAssetAndSendSuccess(AssetButtonEvent event, UserCoinDto tmpCoin, Integer lastMessageId) {
        bagService.deleteAssetByChatId(tmpCoin.getChatId(), event.getCoin());
        userCoinService.deleteByChatId(tmpCoin.getChatId());
        incorrectDeleteMenu.editMsgAndSendSuccess(tmpCoin.getChatId(), lastMessageId);
        myAssetsMenu.sendMenu(event.getChatId());
    }
}

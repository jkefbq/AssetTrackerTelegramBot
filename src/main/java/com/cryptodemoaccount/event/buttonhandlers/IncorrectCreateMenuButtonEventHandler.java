package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncorrectCreateMenuButtonEventHandler {

    private final LastMessageService lastMessageService;
    private final UserCoinService userCoinService;
    private final EnterAssetCountMenu enterAssetCountMenu;

    @EventListener(condition = "event.getButton().name() == 'FORCE_UPDATE_ASSET'")
    public void handleForceUpdateAsset(ButtonEvent event) {
        UserCoinDto tmp = userCoinService.findByChatId(event.getChatId()).orElseThrow();
        tmp.setAssetDo(AssetDo.UPDATE);
        userCoinService.update(tmp);
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        enterAssetCountMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }
}

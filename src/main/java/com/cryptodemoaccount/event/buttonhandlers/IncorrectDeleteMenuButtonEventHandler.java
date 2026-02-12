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
public class IncorrectDeleteMenuButtonEventHandler {

    private final LastMessageService lastMessageService;
    private final UserCoinService userCoinService;
    private final EnterAssetCountMenu enterAssetCountMenu;

    @EventListener(condition = "event.getButton().name() == 'CREATE_ASSET_AFTER_TRY_DELETE'")
    public void handleCreateAssetAfterTryDelete(ButtonEvent event) {
        UserCoinDto tmp = userCoinService.findByChatId(event.getChatId()).orElseThrow();
        tmp.setAssetDo(AssetDo.CREATE);
        userCoinService.create(tmp);
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        enterAssetCountMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }
}

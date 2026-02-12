package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.AssetListMenu;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyAssetsMenuButtonHandler {

    private final LastMessageService lastMessageService;
    private final UserCoinService userCoinService;
    private final AssetListMenu assetListMenu;

    @EventListener(condition = "event.getButton().name() == 'CREATE_ASSET'")
    public void handleCreateAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.CREATE);
        userCoinService.create(dto);
    }

    @EventListener(condition = "event.getButton().name() == 'UPDATE_ASSET'")
    public void handleUpdateAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.UPDATE);
        userCoinService.create(dto);
    }

    @EventListener(condition = "event.getButton().name() == 'DELETE_ASSET'")
    public void handleDeleteAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.DELETE);
        userCoinService.create(dto);
    }
}

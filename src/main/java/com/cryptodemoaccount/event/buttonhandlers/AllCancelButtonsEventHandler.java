package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.assets_menu.AssetsMenu;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.main_menu.MainMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AllCancelButtonsEventHandler {

    private final BagMenu bagMenu;
    private final UserService userService;
    private final LastMessageService lastMessageService;
    private final MyAssetsMenu myAssetsMenu;
    private final UserCoinService userCoinService;
    private final MainMenu mainMenu;
    private final AssetsMenu assetsMenu;

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_MY_ASSETS'")
    public void handleCancelToMyAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        myAssetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        userCoinService.deleteByChatId(event.getChatId());//todo в interceptor
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_MENU'")
    public void handleCancelToMenu(ButtonEvent event) {
        if (userService.isUserWriteQuestion(event.getChatId())) {
            var user = userService.findByChatId(event.getChatId()).orElseThrow();
            user.setStatus(UserStatus.FREE);
            userService.update(user);
        }
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        mainMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_BAG_MENU'")
    public void handleCancelToBagMenu(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        bagMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_ASSETS'")
    public void handleCancelToAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_SEND_BAG_MENU'")
    public void handleCancelToSendBagMenu(ButtonEvent event) {
        bagMenu.sendMenu(event.getChatId());
    }
}

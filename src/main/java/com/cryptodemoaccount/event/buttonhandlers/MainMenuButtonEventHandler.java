package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.my_profile_menu.MyProfileMenu;
import com.cryptodemoaccount.menu.support_menu.SupportMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainMenuButtonEventHandler {

    private final BagMenu bagMenu;
    private final UserService userService;
    private final LastMessageService lastMessageService;
    private final MyProfileMenu profileMenu;
    private final WaitingMenu waitingMenu;
    private final SupportMenu supportMenu;

    @EventListener(condition = "event.getButton.name() == 'MY_BAG'")
    public void handleMyBag(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        bagMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'MY_PROFILE'")
    public void handleMyProfile(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        profileMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'SUPPORT'")
    public void handleSupport(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        var user = userService.findByChatId(event.getChatId()).orElseThrow();
        user.setStatus(UserStatus.WRITING_QUESTION);
        userService.update(user);
        supportMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }
}

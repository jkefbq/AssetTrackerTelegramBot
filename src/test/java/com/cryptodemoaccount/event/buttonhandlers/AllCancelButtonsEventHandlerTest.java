package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.assets_menu.AssetsMenu;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.main_menu.MainMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AllCancelButtonsEventHandlerTest {

    @Mock
    private BagMenu bagMenu;
    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private MyAssetsMenu myAssetsMenu;
    @Mock
    private UserCoinService userCoinService;
    @Mock
    private UserService userService;
    @Mock
    private MainMenu mainMenu;
    @Mock
    private AssetsMenu assetsMenu;

    @InjectMocks
    private AllCancelButtonsEventHandler cancelButtonsHandler;

    @Captor
    private ArgumentCaptor<UserDto> userCaptor;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .userName("username")
                .firstName("firstname")
                .lastName("lastname")
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .status(UserStatus.FREE)
                .build();
    }

    @Test
    public void handleCancelToMyAssetsTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.CANCEL_TO_MY_ASSETS, chatId);

        cancelButtonsHandler.handleCancelToMyAssets(event);

        verify(myAssetsMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleCancelToMenuTest_userWriteQuestion() {
        Long chatId = randomChatId();
        UserDto user = getUserDto();
        user.setStatus(UserStatus.WRITING_QUESTION);
        ButtonEvent event = new ButtonEvent(this, Button.CANCEL_TO_MENU, chatId);
        when(userService.isUserWriteQuestion(chatId)).thenReturn(true);
        when(userService.findByChatId(chatId)).thenReturn(Optional.of(user));

        cancelButtonsHandler.handleCancelToMenu(event);

        verify(userService).update(userCaptor.capture());
        verify(mainMenu).editMsgAndSendMenu(eq(chatId), any());
        assertEquals(UserStatus.FREE, userCaptor.getValue().getStatus());
    }

    @Test
    public void handleCancelToMenuTest_userNotWriteQuestion() {
        Long chatId = randomChatId();
        when(userService.isUserWriteQuestion(chatId)).thenReturn(false);
        ButtonEvent event = new ButtonEvent(this, Button.CANCEL_TO_MENU, chatId);

        cancelButtonsHandler.handleCancelToMenu(event);

        verify(mainMenu).editMsgAndSendMenu(eq(chatId), any());
        verify(userService, never()).update(any());
    }

    @Test
    public void handleCancelToBagMenuTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.CANCEL_TO_BAG_MENU, chatId);

        cancelButtonsHandler.handleCancelToBagMenu(event);

        verify(bagMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleCancelToAssetsTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.CANCEL_TO_ASSETS, chatId);

        cancelButtonsHandler.handleCancelToAssets(event);

        verify(assetsMenu).editMsgAndSendMenu(eq(chatId), any());
    }
}

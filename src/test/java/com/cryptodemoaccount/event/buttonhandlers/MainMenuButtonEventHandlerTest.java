package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.my_profile_menu.MyProfileMenu;
import com.cryptodemoaccount.menu.support_menu.SupportMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MainMenuButtonEventHandlerTest {

    @Captor
    private ArgumentCaptor<UserDto> captor;

    @Mock
    private BagMenu bagMenu;
    @Mock
    private UserService userService;
    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private MyProfileMenu profileMenu;
    @Mock
    private WaitingMenu waitingMenu;
    @Mock
    private SupportMenu supportMenu;

    @InjectMocks
    private MainMenuButtonEventHandler mainMenuButtonHandler;

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
    public void handleMyBagTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.MY_BAG, chatId);

        mainMenuButtonHandler.handleMyBag(event);

        verify(bagMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleMyProfileTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.MY_PROFILE, chatId);

        mainMenuButtonHandler.handleMyProfile(event);

        verify(profileMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleSupportTest() {
        Long chatId = randomChatId();
        when(userService.findByChatId(chatId)).thenReturn(Optional.ofNullable(getUserDto()));
        ButtonEvent event = new ButtonEvent(this, Button.SUPPORT, chatId);

        mainMenuButtonHandler.handleSupport(event);

        verify(userService).update(captor.capture());
        verify(supportMenu).editMsgAndSendMenu(eq(chatId), any());
        assertEquals(UserStatus.WRITING_QUESTION, captor.getValue().getStatus());
    }
}

package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncorrectDeleteMenuButtonEventHandlerTest {

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private UserCoinService userCoinService;
    @Mock
    private EnterAssetCountMenu enterAssetCountMenu;

    @InjectMocks
    private IncorrectDeleteMenuButtonEventHandler incorrectDeleteButtonHandler;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    private UserCoinDto getUserCoinDto() {
        return UserCoinDto.builder()
                .coin(Coins.BITCOIN)
                .count(BigDecimal.TEN)
                .assetDo(AssetDo.CREATE)
                .chatId(randomChatId())
                .id(UUID.randomUUID())
                .build();
    }

    @Test
    public void handleCreateAssetAfterTryDeleteTest() {
        Long chatId = randomChatId();
        when(userCoinService.findByChatId(chatId)).thenReturn(Optional.ofNullable(getUserCoinDto()));
        ButtonEvent event = new ButtonEvent(this, Button.CREATE_ASSET_AFTER_TRY_DELETE, chatId);

        incorrectDeleteButtonHandler.handleCreateAssetAfterTryDelete(event);

        verify(userCoinService).create(any());
        verify(enterAssetCountMenu).editMsgAndSendMenu(eq(chatId), any());
    }
}

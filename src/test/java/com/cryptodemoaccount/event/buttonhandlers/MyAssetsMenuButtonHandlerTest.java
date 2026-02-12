package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.AssetListMenu;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MyAssetsMenuButtonHandlerTest {

    @Captor
    private ArgumentCaptor<UserCoinDto> captor;

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private UserCoinService userCoinService;
    @Mock
    private AssetListMenu assetListMenu;

    @InjectMocks
    private MyAssetsMenuButtonHandler myAssetsButtonHandler;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Test
    public void handleCreateAssetTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.CREATE_ASSET, chatId);

        myAssetsButtonHandler.handleCreateAsset(event);

        verify(userCoinService).create(captor.capture());
        verify(assetListMenu).editMsgAndSendMenu(eq(chatId), any());
        assertEquals(AssetDo.CREATE, captor.getValue().getAssetDo());
    }

    @Test
    public void handleUpdateAssetTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.UPDATE_ASSET, chatId);

        myAssetsButtonHandler.handleUpdateAsset(event);

        verify(userCoinService).create(captor.capture());
        verify(assetListMenu).editMsgAndSendMenu(eq(chatId), any());
        assertEquals(AssetDo.UPDATE, captor.getValue().getAssetDo());
    }

    @Test
    public void handleDeleteAssetTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.DELETE_ASSET, chatId);

        myAssetsButtonHandler.handleDeleteAsset(event);

        verify(userCoinService).create(captor.capture());
        verify(assetListMenu).editMsgAndSendMenu(eq(chatId), any());
        assertEquals(AssetDo.DELETE, captor.getValue().getAssetDo());
    }
}

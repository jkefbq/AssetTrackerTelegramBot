package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.database.service.UserCoinService;
import com.cryptodemoaccount.event.AssetButtonEvent;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.menu.incorrect_create_asset_menu.IncorrectCreateAssetMenu;
import com.cryptodemoaccount.menu.incorrect_delete_menu.IncorrectDeleteMenu;
import com.cryptodemoaccount.menu.incorrect_update_asset_menu.IncorrectUpdateAssetMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
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
public class AssetsListMenuButtonEventHandlerTest {

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private MyAssetsMenu myAssetsMenu;
    @Mock
    private BagService bagService;
    @Mock
    private UserCoinService userCoinService;
    @Mock
    private IncorrectUpdateAssetMenu incorrectUpdateAssetMenu;
    @Mock
    private IncorrectCreateAssetMenu incorrectCreateAssetMenu;
    @Mock
    private EnterAssetCountMenu enterAssetCountMenu;
    @Mock
    private IncorrectDeleteMenu incorrectDeleteMenu;
    @Mock
    private WaitingMenu waitingMenu;

    @InjectMocks
    private AssetsListMenuButtonEventHandler assetsListButtonHandler;

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
    public void handleAnyAssetButtonTest() {
        Long chatId = randomChatId();
        when(userCoinService.findByChatId(chatId)).thenReturn(Optional.ofNullable(getUserCoinDto()));
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);

        assetsListButtonHandler.handleAnyAssetButton(event);

        verify(userCoinService).create(any(UserCoinDto.class));
    }

    @Test
    public void processAssetTest_assertIncorrectCreateAsset() {
        var chatId = randomChatId();
        var messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.CREATE);
        when(bagService.hasCoin(chatId, Coins.BITCOIN)).thenReturn(true);

        assetsListButtonHandler.processAsset(event, userCoinDto, messageId);

        verify(incorrectCreateAssetMenu).editMsgAndSendMenu(eq(chatId), eq(messageId));
    }

    @Test
    public void processAssetTest_assertIncorrectUpdateAsset() {
        Long chatId = randomChatId();
        int messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.UPDATE);

        when(bagService.hasCoin(chatId, Coins.BITCOIN)).thenReturn(false);

        assetsListButtonHandler.processAsset(event, userCoinDto, messageId);

        verify(incorrectUpdateAssetMenu).editMsgAndSendMenu(eq(chatId), eq(messageId));
    }

    @Test
    public void processAssetTest_assertCorrectDeleteAsset() {
        Long chatId = randomChatId();
        int messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.DELETE);

        when(bagService.hasCoin(chatId, Coins.BITCOIN)).thenReturn(true);

        assetsListButtonHandler.processAsset(event, userCoinDto, messageId);

        verify(bagService).deleteAssetByChatId(chatId, Coins.BITCOIN);
        verify(userCoinService).deleteByChatId(chatId);
    }

    @Test
    public void processAssetTest_assertIncorrectDeleteAsset() {
        Long chatId = randomChatId();
        int messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.DELETE);

        when(bagService.hasCoin(chatId, Coins.BITCOIN)).thenReturn(false);

        assetsListButtonHandler.processAsset(event, userCoinDto, messageId);

        verify(incorrectDeleteMenu).editMsgAndSendMenu(eq(chatId), eq(messageId));
    }

    @Test
    public void processAssetTest_assertEnterCountMenu() {
        Long chatId = randomChatId();
        int messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.CREATE);

        when(bagService.hasCoin(chatId, Coins.BITCOIN)).thenReturn(false);

        assetsListButtonHandler.processAsset(event, userCoinDto, messageId);

        verify(enterAssetCountMenu).editMsgAndSendMenu(eq(chatId), eq(messageId));
    }

    @Test
    public void deleteAssetAndSendSuccessTest() {
        Long chatId = randomChatId();
        int messageId = ThreadLocalRandom.current().nextInt();
        AssetButtonEvent event = new AssetButtonEvent(this, Coins.BITCOIN, chatId);
        UserCoinDto userCoinDto = new UserCoinDto(chatId, AssetDo.CREATE);

        assetsListButtonHandler.deleteAssetAndSendSuccess(event, userCoinDto, messageId);

        verify(bagService).deleteAssetByChatId(chatId, Coins.BITCOIN);
        verify(userCoinService).deleteByChatId(chatId);
        verify(incorrectDeleteMenu).editMsgAndSendSuccess(eq(chatId), eq(messageId));
        verify(myAssetsMenu).sendMenu(chatId);
    }
}

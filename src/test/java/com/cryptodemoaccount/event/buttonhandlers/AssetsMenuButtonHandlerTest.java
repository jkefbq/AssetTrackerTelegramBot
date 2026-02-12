package com.cryptodemoaccount.event.buttonhandlers;

import com.cryptodemoaccount.event.Button;
import com.cryptodemoaccount.event.ButtonEvent;
import com.cryptodemoaccount.menu.asset_statistics_menu.AssetStatisticsMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AssetsMenuButtonHandlerTest {

    @Mock
    private LastMessageService lastMessageService;
    @Mock
    private MyAssetsMenu myAssetsMenu;
    @Mock
    private WaitingMenu waitingMenu;
    @Mock
    private AssetStatisticsMenu assetStatisticsMenu;

    @InjectMocks
    private AssetsMenuButtonHandler assetsButtonHandler;

    private Long randomChatId() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Test
    public void handleMyAssetsTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.MY_ASSETS, chatId);

        assetsButtonHandler.handleMyAssets(event);

        verify(myAssetsMenu).editMsgAndSendMenu(eq(chatId), any());
    }

    @Test
    public void handleAssetStatisticsTest() {
        Long chatId = randomChatId();
        ButtonEvent event = new ButtonEvent(this, Button.ASSET_STATISTICS, chatId);

        assetsButtonHandler.handleAssetStatistics(event);

        verify(assetStatisticsMenu).editMsgAndSendMenu(eq(chatId), any());
    }
}

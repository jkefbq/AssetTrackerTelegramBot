package com.cryptodemoaccount.events;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.service.AssetService;
import com.cryptodemoaccount.database.service.BagService;
import com.cryptodemoaccount.database.service.UserService;
import com.cryptodemoaccount.menu.ai_advice_menu.AIAdviceMenu;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.AssetListMenu;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.menu.asset_statistics_menu.AssetStatisticsMenu;
import com.cryptodemoaccount.menu.assets_menu.AssetsMenu;
import com.cryptodemoaccount.menu.bag_menu.BagMenu;
import com.cryptodemoaccount.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.cryptodemoaccount.menu.incorrect_create_asset_menu.IncorrectCreateAssetMenu;
import com.cryptodemoaccount.menu.incorrect_delete_menu.IncorrectDeleteMenu;
import com.cryptodemoaccount.menu.incorrect_update_asset_menu.IncorrectUpdateAssetMenu;
import com.cryptodemoaccount.menu.main_menu.MainMenu;
import com.cryptodemoaccount.menu.my_assets_menu.MyAssetsMenu;
import com.cryptodemoaccount.menu.my_profile_menu.MyProfileMenu;
import com.cryptodemoaccount.menu.support_menu.SupportMenu;
import com.cryptodemoaccount.menu.trade_with_ai_menu.TradeWithAIMenu;
import com.cryptodemoaccount.menu.waiting_menu.WaitingMenu;
import com.cryptodemoaccount.service.LastMessageService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ButtonEventHandler {

    private final BagMenu bagMenu;
    private final UserService userService;
    private final LastMessageService lastMessageService;
    private final MyAssetsMenu myAssetsMenu;
    private final BagService bagService;
    private final AssetService assetService;
    private final IncorrectUpdateAssetMenu incorrectUpdateAssetMenu;
    private final IncorrectCreateAssetMenu incorrectCreateAssetMenu;
    private final AssetListMenu assetListMenu;
    private final EnterAssetCountMenu enterAssetCountMenu;
    private final IncorrectDeleteMenu incorrectDeleteMenu;
    private final MyProfileMenu profileMenu;
    private final MainMenu mainMenu;
    private final WaitingMenu waitingMenu;
    private final AssetsMenu assetsMenu;
    private final AssetStatisticsMenu assetStatisticsMenu;
    private final TradeWithAIMenu tradeWithAIMenu;
    private final AIAdviceMenu aiAdviceMenu;
    private final SupportMenu supportMenu;

    @EventListener(condition = "event.getButton.name() == 'MY_BAG'")
    public void handleMyBag(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        bagMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CREATE_ASSET'")
    public void handleCreateAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.CREATE);
        assetService.create(dto);
    }

    @EventListener(condition = "event.getButton().name() == 'UPDATE_ASSET'")
    public void handleUpdateAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.UPDATE);
        assetService.create(dto);
    }

    @EventListener(condition = "event.getButton().name() == 'FORCE_UPDATE_ASSET'")
    public void handleForceUpdateAsset(ButtonEvent event) {
        UserCoinDto tmp = assetService.findByChatId(event.getChatId()).orElseThrow();
        tmp.setAssetDo(AssetDo.UPDATE);
        assetService.create(tmp);
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        enterAssetCountMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'FORCE_CREATE_ASSET'")
    public void handleForceCreateAsset(ButtonEvent event) {
        UserCoinDto tmp = assetService.findByChatId(event.getChatId()).orElseThrow();
        tmp.setAssetDo(AssetDo.CREATE);
        assetService.create(tmp);
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        enterAssetCountMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CREATE_ASSET_AFTER_TRY_DELETE'")
    public void handleCreateAssetAfterTryDelete(ButtonEvent event) {
        UserCoinDto tmp = assetService.findByChatId(event.getChatId()).orElseThrow();
        tmp.setAssetDo(AssetDo.CREATE);
        assetService.create(tmp);
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        enterAssetCountMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_MY_ASSETS'")
    public void handleCancelToMyAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        myAssetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        assetService.deleteByChatId(event.getChatId());
    }

    @EventListener(condition = "event.getButton().name() == 'DELETE_ASSET'")
    public void handleDeleteAsset(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetListMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        UserCoinDto dto = new UserCoinDto(event.getChatId(), AssetDo.DELETE);
        assetService.create(dto);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_MENU'")
    public void handleCancelToMenu(ButtonEvent event) {
        if (userService.isUserWriteQuestion(event.getChatId())) {
            var user = userService.findByChatId(event.getChatId()).orElseThrow();
            user.setStatus(UserStatus.FREE);
            userService.create(user);
        }
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        mainMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'MY_PROFILE'")
    public void handleMyProfile(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        profileMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_BAG_MENU'")
    public void handleCancelToBagMenu(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        bagMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @SneakyThrows
    @EventListener(condition = "event.getButton().name() == 'UPDATE_BAG_DATA'")
    public void handleUpdateBagData(ButtonEvent event) {
        BagDto bag = bagService.findByChatId(event.getChatId()).orElseThrow();
        bagService.actualizeBagFields(bag);

        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        bagMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'ASSETS'")
    public void handleAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_ASSETS'")
    public void handleCancelToAssets(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'MY_ASSETS'")
    public void handleMyAssets(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        myAssetsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'ASSET_STATISTICS'")
    public void handleAssetStatistics(ButtonEvent event) {
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        assetStatisticsMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'TRADE_WITH_AI'")
    public void handleTradeWithAI(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        tradeWithAIMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'AI_ADVICE'")
    public void handleAIAdvice(ButtonEvent event) {
        waitingMenu.sendMenu(event.getChatId());
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        try {
            aiAdviceMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } catch (RequestNotPermitted e) {
            aiAdviceMenu.sendToManyRequests(event.getChatId());
        }
    }

    @EventListener(condition = "event.getButton().name() == 'SUPPORT'")
    public void handleSupport(ButtonEvent event) {
        Integer lastMessageId = lastMessageService.getLastMessage(event.getChatId());
        var user = userService.findByChatId(event.getChatId()).orElseThrow();
        user.setStatus(UserStatus.WRITING_QUESTION);
        userService.update(user);
        supportMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
    }

    @EventListener
    public void handleAnyAssetButton(AssetButtonEvent event) {
        var tmpCoin = assetService.findByChatId(event.getChatId()).orElseThrow();
        var lastMessageId = lastMessageService.getLastMessage(event.getChatId());

        tmpCoin.setCoin(event.getCoin());
        waitingMenu.editMsgAndSendMenu(event.getChatId(), lastMessageService.getLastMessage(event.getChatId()));
        assetService.create(tmpCoin);
        processAsset(event, tmpCoin, lastMessageId);
    }

    @EventListener(condition = "event.getButton().name() == 'CANCEL_TO_SEND_BAG_MENU'")
    public void handleCancelToSendBagMenu(ButtonEvent event) {
        bagMenu.sendMenu(event.getChatId());
    }

    private void processAsset(AssetButtonEvent event, UserCoinDto tmpCoin, Integer lastMessageId) {
        boolean hasCoin = bagService.hasCoin(event.getChatId(), event.getCoin());

        if (tmpCoin.getAssetDo() == AssetDo.CREATE && hasCoin) {
            incorrectCreateAssetMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.UPDATE && !hasCoin) {
            incorrectUpdateAssetMenu.editMsgAndSendMenu(event.getChatId(), lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.DELETE && hasCoin) {
            deleteAssetAndSendSuccess(event, tmpCoin, lastMessageId);
        } else if (tmpCoin.getAssetDo() == AssetDo.DELETE && !hasCoin) {
            incorrectDeleteMenu.editMsgAndSendMenu(tmpCoin.getChatId(), lastMessageId);
        } else {
            enterAssetCountMenu.editMsgAndSendMenu(tmpCoin.getChatId(), lastMessageId);
        }
    }

    private void deleteAssetAndSendSuccess(AssetButtonEvent event, UserCoinDto tmpCoin, Integer lastMessageId) {
        bagService.deleteByChatId(tmpCoin.getChatId(), event.getCoin());
        assetService.deleteByChatId(tmpCoin.getChatId());
        incorrectDeleteMenu.EditMsgAndSendSuccess(tmpCoin.getChatId(), lastMessageId);
        myAssetsMenu.sendMenu(event.getChatId());
    }
}

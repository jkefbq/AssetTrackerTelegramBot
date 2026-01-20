package com.assettracker.main.telegram_bot.events;

import com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu.UserCoin;
import com.assettracker.main.telegram_bot.buttons.menu.bag_menu.BagMenu;
import com.assettracker.main.telegram_bot.buttons.menu.enter_asset_count_menu.EnterAssetCountMenu;
import com.assettracker.main.telegram_bot.buttons.menu.incorrect_update_asset_menu.IncorrectUpdateAssetMenu;
import com.assettracker.main.telegram_bot.buttons.menu.main_menu.MainMenu;
import com.assettracker.main.telegram_bot.database.service.BagService;
import com.assettracker.main.telegram_bot.database.service.DataInitializerService;
import com.assettracker.main.telegram_bot.service.AssetService;
import com.assettracker.main.telegram_bot.service.ChatId;
import com.assettracker.main.telegram_bot.service.LastMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public class MessageEventHandler {//TODO тут однотипное логгирование, сделать через AOP

    private final ExecutorService es = Executors.newFixedThreadPool(10);
    private final MainMenu mainMenu;
    private final BagMenu bagMenu;
    private final BagService bagService;
    private final DataInitializerService initializer;
    private final AssetService assetService;
    private final EnterAssetCountMenu enterAssetCountMenu;
    private final IncorrectUpdateAssetMenu incorrectUpdateAssetMenu;
    private final LastMessageService lastMessageService;

    @EventListener(condition = "event.getMessage().name() == 'START'")
//todo перед каждым проверить вдруг чел должен был ввести число, соответственоо сбросить tmp
    public void handleStart(MessageEvent event) {
        log.info("about to handle '{}' message", event.getMessage().getText());
        var chatId = ChatId.get(event.getUpdate());
        mainMenu.sendMenu(chatId);
        es.execute(() -> {
            if (bagService.findByChatId(chatId).isEmpty()) {
                initializer.initializeUserAndBag(event.getUpdate());
            }
        });
        log.info("message '{}' handled successfully", event.getMessage().getText());
    }

    @EventListener(condition = "event.getMessage().name() == 'MENU'")
    public void handleMenu(MessageEvent event) {
        log.info("about to handle '{}' message", event.getMessage().getText());
        var chatId = ChatId.get(event.getUpdate());
        mainMenu.sendMenu(chatId);
        log.info("message '{}' handled successfully", event.getMessage().getText());
    }

    @EventListener(condition = "event.getMessage().name() == 'BAG'")
    public void handleBag(MessageEvent event) {
        log.info("about to handle '{}' message", event.getMessage().getText());
        bagMenu.sendMenu(ChatId.get(event.getUpdate()));
        log.info("message '{}' handled successfully", event.getMessage().getText());
    }

    @EventListener(condition = "event.getMessage().name() == 'UNKNOWN'")
    public void handleUnknown(MessageEvent event) {
        log.info("about to handle unknown message='{}'", event.getUpdate().getMessage().getText());
        var chatId = ChatId.get(event.getUpdate());
        if (assetService.isUserWaitingNumber(chatId)) {
            var coinCount = BigDecimal.valueOf(
                    Double.parseDouble(event.getUpdate().getMessage().getText().trim()));
            UserCoin tmpCoin = assetService.getTmpUserCoin(chatId);
            tmpCoin.setCount(coinCount);
            bagService.addAsset(tmpCoin);
            assetService.deleteTmpUserCoin(chatId);
            enterAssetCountMenu.sendSuccess(chatId);
            bagMenu.sendMenu(chatId);
        }
        log.info("handling message='{}' finished", event.getUpdate().getMessage().getText());
    }

    public void addAsset(UserCoin coin) {

    }
}

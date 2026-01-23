package com.assettracker.main.telegram_bot.menu.asset_list_menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class EthereumButton implements IAssetListMenuButton, IAsset {

    @Getter
    private final String callbackData = Coins.ETHEREUM.getIdsName();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("ETH")
                .callbackData(callbackData)
                .build();
    }

    @Override
    public Coins getCoin() {
        return Coins.ETHEREUM;
    }
}
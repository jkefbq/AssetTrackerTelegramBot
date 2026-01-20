package com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class BitcoinButton implements IAssetListMenuButton, IAsset {

    @Getter
    private final String callbackData = Coins.BITCOIN.getIdsName();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("BTC")
                .callbackData(callbackData)
                .build();
    }

    @Override
    public Coins getCoin() {
        return Coins.BITCOIN;
    }
}

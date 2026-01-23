package com.assettracker.main.telegram_bot.menu.asset_list_menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class LiteCoinButton implements IAssetListMenuButton, IAsset {

    @Getter
    private final String callbackData = Coins.LITECOIN.getIdsName();

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text("LTC")
                .callbackData(callbackData)
                .build();
    }

    @Override
    public Coins getCoin() {
        return Coins.LITECOIN;
    }
}
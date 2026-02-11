package com.cryptodemoaccount.menu.asset_list_menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@AllArgsConstructor
public class TronButton implements IAssetListMenuButton, IAsset {

    @Getter
    private final String callbackData = Coins.TRON.getIdsName();
    private final String text = "TRX";

    @Override
    public InlineKeyboardButton getButton() {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

    @Override
    public Coins getCoin() {
        return Coins.TRON;
    }
}
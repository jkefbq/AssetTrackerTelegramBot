package com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Coins {
    BITCOIN("bitcoin"),
    ETHEREUM("ethereum"),
    SOLANA("solana"),
    PEPE("pepe"),
    DOGECOIN("dogecoin"),
    TETHER("tether"),
    TRON("tron"),
    BNB("binancecoin"),
    XRP("ripple"),
    LITECOIN("litecoin");

    private final String idsName;

    Coins(String idsName) {
        this.idsName = idsName;
    }

    public static Coins getCoinForIds(String ids) {
        return Arrays.stream(Coins.values()).filter(coin ->
                coin.getIdsName().equals(ids)).findFirst().orElseThrow();
    }
}
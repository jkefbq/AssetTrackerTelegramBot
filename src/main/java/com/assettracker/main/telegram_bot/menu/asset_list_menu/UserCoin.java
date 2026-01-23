package com.assettracker.main.telegram_bot.menu.asset_list_menu;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class UserCoin implements Serializable {

    private UUID id;
    private final Long chatId;
    @Setter
    private Coins coin;
    private BigDecimal count;
    @Setter
    private AssetDo assetDo;


    public UserCoin(UUID id, Coins coin, Long chatId, AssetDo assetDo) {
        this.id = id;
        this.chatId = chatId;
        this.coin = coin;
        this.assetDo = assetDo;
    }

    public UserCoin(Coins coin, Long chatId, AssetDo assetDo) {
        this.chatId = chatId;
        this.coin = coin;
        this.assetDo = assetDo;
    }

    public UserCoin(Long chatId, AssetDo assetDo) {
        this.chatId = chatId;
        this.assetDo = assetDo;
    }

    public void setCount(BigDecimal count) {
        if (count.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("coin count must be greater then 0");
        }
        this.count = count;
    }
}

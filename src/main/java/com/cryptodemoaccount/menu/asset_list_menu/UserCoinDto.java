package com.cryptodemoaccount.menu.asset_list_menu;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class UserCoinDto implements Serializable {

    @Setter
    private UUID id;
    @Setter
    private Long chatId;
    @Setter
    private Coins coin;
    private BigDecimal count;
    @Setter
    private AssetDo assetDo;

    @Builder
    public UserCoinDto(UUID id, Long chatId, Coins coin, BigDecimal count, AssetDo assetDo) {
        this.id = id;
        this.chatId = chatId;
        this.coin = coin;
        this.count = count;
        this.assetDo = assetDo;
    }

    public UserCoinDto(Long chatId, AssetDo assetDo) {
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

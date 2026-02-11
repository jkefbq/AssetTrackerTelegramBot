package com.cryptodemoaccount.database.entity;

import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "user_coins")
@Entity
@NoArgsConstructor
@Setter
@Getter
public class UserCoinEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Coins coin;

    private BigDecimal count;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_do")
    private AssetDo assetDo;

    @Column(name = "chat_id")
    private Long chatId;

    public UserCoinEntity(UserCoinDto userCoinDto) {
        this.id = userCoinDto.getId();
        this.coin = userCoinDto.getCoin();
        this.count = userCoinDto.getCount();
        this.chatId = userCoinDto.getChatId();
        this.assetDo = userCoinDto.getAssetDo();
    }
}



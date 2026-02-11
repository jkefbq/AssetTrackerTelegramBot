package com.cryptodemoaccount.database.dto;

import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BagDto {

    private UUID id;
    private Long chatId;
    private Long version;

    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    @Builder.Default
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Builder.Default
    private Integer assetCount = 0;

    @Builder.Default
    private Map<Coins, BigDecimal> assets = new HashMap<>();
}

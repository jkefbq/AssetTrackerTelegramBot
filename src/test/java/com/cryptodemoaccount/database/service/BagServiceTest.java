package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.entity.BagEntity;
import com.cryptodemoaccount.database.mapper.BagMapperImpl;
import com.cryptodemoaccount.database.repository.BagRepository;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.service.MarketInfoKeeper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BagServiceTest {

    private static final Long CHAT_ID = ThreadLocalRandom.current().nextLong();

    @Mock
    BagRepository bagRepository;
    @Mock
    BagMapperImpl bagMapper;
    @Mock
    MarketInfoKeeper marketInfoKeeper;

    @Spy
    @InjectMocks
    BagService bagService;

    private BagDto getBagDto() {
        return BagDto.builder()
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .createdAt(LocalDate.now())
                .totalCost(BigDecimal.ZERO)
                .assets(new HashMap<>())
                .build();
    }

    private BagEntity getBagEntity() {
        return new BagEntity();
    }

    private Map<Coins, BigDecimal> getCoinCount() {
        return new HashMap<>(Map.of(
                Coins.BITCOIN, BigDecimal.ONE,
                Coins.BNB, BigDecimal.TWO,
                Coins.SOLANA, BigDecimal.TEN
        ));
    }

    @Test
    public void createTest() {
        var bag = getBagDto();

        bagService.create(bag);

        verify(bagRepository, times(1)).save(any());
    }

    @Test
    public void updateBagTest() {
        var bag = getBagDto();

        bagService.update(bag);

        verify(bagRepository, times(1)).save(any());
    }

    @Test
    public void findByChatIdTest() {
        bagService.findByChatId(CHAT_ID);
        verify(bagRepository, times(1)).findByChatId(CHAT_ID);
    }

    @Test
    public void deleteAssetByChatIdTest() {
        doReturn(Optional.of(getBagDto())).when(bagService).findByChatId(CHAT_ID);

        bagService.deleteAssetByChatId(CHAT_ID, Coins.BITCOIN);

        verify(bagService).findByChatId(CHAT_ID);
    }

    @Test
    public void addAssetTest() {
        try {
            bagService.addAsset(
                    UserCoinDto.builder()
                            .coin(Coins.XRP)
                            .assetDo(AssetDo.CREATE)
                            .chatId(CHAT_ID)
                            .count(BigDecimal.TEN)
                            .build()
            );
        } catch (Exception ignored) {}

        verify(bagService).findByChatId(CHAT_ID);
    }

    @Test
    public void getCoinCountAndPricesTest() throws JsonProcessingException {
        BagDto bag = getBagDto();
        bag.setAssets(getCoinCount());
        when(bagService.findByChatId(CHAT_ID)).thenReturn(Optional.of(bag));

        bagService.getCoinCountAndPrices(CHAT_ID);

        verify(marketInfoKeeper).getCoinPrices(any());
    }

    @Test
    public void getCoinChangesTest() throws JsonProcessingException {
        when(bagService.findByChatId(CHAT_ID)).thenReturn(Optional.of(getBagDto()));

        bagService.getCoinChanges(CHAT_ID);

        verify(marketInfoKeeper).getCoinChanges(any());
    }
}

package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.mapper.BagMapper;
import com.cryptodemoaccount.database.repository.BagRepository;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import com.cryptodemoaccount.service.MarketInfoKeeper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BagService {

    private final BagMapper mapper;
    private static final String CACHE_NAMES = "bags";
    private final BagRepository bagRepo;
    private final MarketInfoKeeper marketInfoKeeper;

    @Transactional
    public BagDto create(BagDto dto) {
        return mapper.toDto(
                bagRepo.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Transactional
    @CachePut(cacheNames = CACHE_NAMES, key = "#entity.getChatId", unless = "#result == null")
    public BagDto update(BagDto dto) {
        return mapper.toDto(
                bagRepo.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Transactional
    @Cacheable(cacheNames = CACHE_NAMES, key = "#chatId", unless = "#result == null")
    public Optional<BagDto> findByChatId(Long chatId) {
        return bagRepo.findByChatId(chatId).map(mapper::toDto);
    }

    @Transactional
    public void deleteAssetByChatId(Long chatId, Coins coin) {
        BagDto bag = findByChatId(chatId).orElseThrow();
        bag.getAssets().remove(coin);
        try {
            actualizeBagFields(bag);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("exception for call coinGecko api method");
        }
    }

    @SneakyThrows
    @Transactional
    public BagDto addAsset(UserCoinDto userCoinDto) {
        log.info("add or update asset {name={}, count={}} into bag with chatId={}",
                userCoinDto.getCoin().name(), userCoinDto.getCount(), userCoinDto.getChatId());
        BagDto bag = findByChatId(userCoinDto.getChatId()).orElseThrow();
        bag.getAssets().put(userCoinDto.getCoin(), userCoinDto.getCount());
        return actualizeBagFields(bag);
    }

    @Transactional
    public BagDto actualizeBagFields(BagDto dto) throws IOException, InterruptedException {
        Map<Coins, BigDecimal> coinPrices = marketInfoKeeper.getCoinPrices(dto.getAssets().keySet());

        BigDecimal totalCost = dto.getAssets().entrySet().stream()
                .map(entry -> coinPrices.get(entry.getKey()).multiply(entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalCost(totalCost);
        dto.setAssetCount(dto.getAssets().size());
        return dto;
    }

    @SneakyThrows
    @Transactional
    public Map<Coins, Map.Entry<BigDecimal, BigDecimal>> getCoinCountAndPrices(Long chatId) {
        Map<Coins, BigDecimal> source = findByChatId(chatId).orElseThrow().getAssets();
        Map<Coins, Map.Entry<BigDecimal, BigDecimal>> result = new HashMap<>();
        marketInfoKeeper.getCoinPrices(source.keySet())
                .forEach((coin, price) ->
                        result.put(coin, Map.entry(source.get(coin), price))
                );
        return result;
    }

    @SneakyThrows
    @Transactional
    public Map<Coins, Map.Entry<BigDecimal, BigDecimal>> getCoinChanges(Long chatId) {
        BagDto bag = findByChatId(chatId).orElseThrow();
        return marketInfoKeeper.getCoinChanges(bag.getAssets().keySet());
    }

    @Transactional
    public boolean hasCoin(Long chatId, Coins coin) {
        return findByChatId(chatId).orElseThrow()
                .getAssets().keySet().stream()
                .anyMatch(userCoin -> userCoin == coin);
    }
}

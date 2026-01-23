package com.assettracker.main.telegram_bot.service;

import com.assettracker.main.telegram_bot.menu.asset_list_menu.Coins;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class MarketInfoKeeper {


    private final String GECKO_KEY;
    private final String SIMPLE_PRICE_URL;
    private final ObjectMapper mapper;

    public MarketInfoKeeper(
            @Value("${GECKO_KEY}") String GECKO_KEY,
            @Value("${api.url.simple-price}") String SIMPLE_PRICE_URL,
            ObjectMapper mapper
    ) {
        this.GECKO_KEY = GECKO_KEY;
        this.SIMPLE_PRICE_URL = SIMPLE_PRICE_URL;
        this.mapper = mapper;
    }

    public Map<Coins, BigDecimal> getCoinPrices(Set<Coins> coins)
            throws IOException, InterruptedException {
        String coinsIdsString = String.join(",", coins.stream().map(Coins::getIdsName).toList());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SIMPLE_PRICE_URL +
                        "?vs_currencies=usd&precision=10&ids=" + coinsIdsString))
                .header("x-cg-demo-api-key", GECKO_KEY)
                .GET()
                .build();
        log.info("about to send https request to retrieve coin prices");
        String json = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
        return toResultCoinPriceMap(json);
    }

    public Map<Coins, Map.Entry<BigDecimal, BigDecimal>> getCoinChanges(Set<Coins> coins)
            throws IOException, InterruptedException {
        String coinsIdsString = String.join(",", coins.stream().map(Coins::getIdsName).toList());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SIMPLE_PRICE_URL +
                        "?vs_currencies=usd&precision=10&include_24hr_change=true&ids=" + coinsIdsString))
                .header("x-cg-demo-api-key", GECKO_KEY)
                .GET()
                .build();
        log.info("about to send https request to retrieve coin changes");
        String json = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
        return toResultCoinChangeMap(json);
    }

    private Map<Coins, Map.Entry<BigDecimal, BigDecimal>> toResultCoinChangeMap(String json) throws JsonProcessingException {
        Map<Coins, Map.Entry<BigDecimal, BigDecimal>> result = new HashMap<>();

        JsonNode jsonNode = mapper.readTree(json);
        jsonNode.forEachEntry((coinIds, node) -> {
            result.put(
                    Coins.getCoinForIds(coinIds),
                    Map.entry(
                            node.get("usd_24h_change").decimalValue(), node.get("usd").decimalValue()
                    )
            );
        });

        return result;
    }


    private Map<Coins, BigDecimal> toResultCoinPriceMap(String json) throws JsonProcessingException {
        Map<Coins, BigDecimal> result = new HashMap<>();

        JsonNode jsonNode = mapper.readTree(json);
        jsonNode.forEachEntry((coinIds, node) ->
                result.put(Coins.getCoinForIds(coinIds), node.get("usd").decimalValue()));
        return result;
    }
}


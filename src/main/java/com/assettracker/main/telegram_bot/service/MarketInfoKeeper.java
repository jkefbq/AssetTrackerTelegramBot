package com.assettracker.main.telegram_bot.service;

import com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu.Coins;
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
                        "?vs_currencies=usd&ids=" + coinsIdsString))
                .header("x-cg-demo-api-key", GECKO_KEY)
                .GET()
                .build();
        log.info("create https request to retrieve coin prices");
        String json = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();
        return toResultMap(json);
    }


    public Map<Coins, BigDecimal> toResultMap(String json) throws JsonProcessingException {
        Map<Coins, BigDecimal> result = new HashMap<>();

        JsonNode jsonNode = mapper.readTree(json);
        jsonNode.forEachEntry((k, v) ->
                result.put(Coins.getCoinForIds(k), v.get("usd").decimalValue()));
        return result;
    }
}


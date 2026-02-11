package com.cryptodemoaccount.service;

import com.cryptodemoaccount.config.YamlConfig;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class MarketInfoKeeper {

    private final String API_KEY_HEADER;
    private final String GECKO_KEY;
    private final String SIMPLE_PRICE_URL;
    private final String CURRENCIES_PARAM = "vs_currencies=usd";
    private final String CURRENCIES_JSON = "usd";
    private final String PRECISION = "precision=10";
    private final String CHANGES_PARAM = "include_24hr_change=true";
    private final String CHANGES_JSON = "usd_24h_change";
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public MarketInfoKeeper(YamlConfig config, ObjectMapper mapper, RestTemplate restTemplate) {
        this.API_KEY_HEADER = config.getApi().getGecko().getHeaders().getApiKeyHeader();
        this.GECKO_KEY = config.getApi().getGecko().getKey();
        this.SIMPLE_PRICE_URL = config.getApi().getGecko().getUrls().getSimplePrice();
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public Map<Coins, BigDecimal> getCoinPrices(Set<Coins> coins) throws JsonProcessingException {
        String coinsIdsString = String.join(",", coins.stream().map(Coins::getIdsName).toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY_HEADER, GECKO_KEY);
        HttpEntity<String> http = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                SIMPLE_PRICE_URL + "?" + CURRENCIES_PARAM + "&" + PRECISION + "&ids=" + coinsIdsString,
                HttpMethod.GET,
                http,
                String.class
        );
        return toResultCoinPriceMap(response.getBody());
    }

    /** returns Map[Coin, [change(%), price(usd)]] */
    public Map<Coins, Map.Entry<BigDecimal, BigDecimal>> getCoinChanges(Set<Coins> coins) throws JsonProcessingException {
        String coinsIdsString = String.join(",", coins.stream().map(Coins::getIdsName).toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add(API_KEY_HEADER, GECKO_KEY);
        HttpEntity<String> http = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                SIMPLE_PRICE_URL + "?" + CURRENCIES_PARAM + "&" + PRECISION +
                        "&" + CHANGES_PARAM + "&ids=" + coinsIdsString,
                HttpMethod.GET,
                http,
                String.class
        );
        return toResultCoinChangeMap(response.getBody());
    }

    private Map<Coins, Map.Entry<BigDecimal, BigDecimal>> toResultCoinChangeMap(
            String json
    ) throws JsonProcessingException {
        Map<Coins, Map.Entry<BigDecimal, BigDecimal>> result = new HashMap<>();

        JsonNode jsonNode = mapper.readTree(json);
        jsonNode.forEachEntry((coinIds, node) -> {
            result.put(Coins.getCoinForIds(coinIds),
                    Map.entry(
                            node.get(CHANGES_JSON).decimalValue(), node.get(CURRENCIES_JSON).decimalValue()
                    )
            );
        });
        return result;
    }


    private Map<Coins, BigDecimal> toResultCoinPriceMap(String json) throws JsonProcessingException {
        Map<Coins, BigDecimal> result = new HashMap<>();

        JsonNode jsonNode = mapper.readTree(json);
        jsonNode.forEachEntry((coinIds, node) ->
                result.put(Coins.getCoinForIds(coinIds), node.get(CURRENCIES_JSON).decimalValue()));
        return result;
    }
}


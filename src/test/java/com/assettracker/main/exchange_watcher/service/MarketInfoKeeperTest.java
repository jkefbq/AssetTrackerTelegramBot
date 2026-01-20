//package com.assettracker.main.exchange_watcher.service;
//
//import com.assettracker.main.telegram_bot.buttons.menu.asset_list_menu.Coins;
//import com.assettracker.main.telegram_bot.database.service.BagService;
//import com.assettracker.main.telegram_bot.service.MarketInfoKeeper;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public class MarketInfoKeeperTest {
//
//    private MarketInfoKeeper marketInfoKeeper;
//    private BagService bagDbService;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void test() throws JsonProcessingException {
//        var json ="{\n" +
//                      "\"BITCOIN\": {\n" +
//                        "\"usd\":123.52\n" +
//                        "},\n" +
//                      "\"ETHEREUM\": {\n" +
//                        "\"usd\":234.74\n" +
//                        "}\n" +
//                   "}";
//        Map<Coins, BigDecimal> result = new HashMap<>();
//
//        JsonNode jsonNode = objectMapper.readTree(json);
//        jsonNode.forEachEntry((k, v) -> {
//            result.put(Coins.valueOf(k), v.get("usd").decimalValue());
//        });
//
//        System.out.println(result);
//    }
//
//    @Test
//    public void pa() {
//        Set<Coins> coins = Set.of(Coins.BNB, Coins.BITCOIN, Coins.ETHEREUM);
//
//        List<String> list = coins.stream().map(Coins::getIdsName).toList();
//        String result = String.join(",", list);
//        System.out.println(result);
//    }

//    @Test
//    public void test2() throws IOException, InterruptedException {
//        BagDto bag = BagDto.builder()
//                .assets(new HashMap<>())
//                .createdAt(LocalDate.now())
//                .chatId(ThreadLocalRandom.current().nextLong())
//                .totalCost(BigDecimal.ZERO)
//                .assetCount(0)
//                .build();
//        BagDto upd = bagDbService.actualizeBagFields(bag);
//        System.out.println(upd);
//    }
//}

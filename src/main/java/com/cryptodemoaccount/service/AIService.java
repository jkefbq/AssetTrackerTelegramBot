package com.cryptodemoaccount.service;

import com.cryptodemoaccount.config.YamlConfig;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.google.genai.Client;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
@Service
public class AIService {

    private static final String AI_MODEL = "gemini-2.5-flash";
    private static final String ADVICE_PROMPT;
    private  final Client client;

    static {
        try {
            ADVICE_PROMPT = Files.readString(Path.of("src/main/resources/static/ai-advice-prompt.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AIService(YamlConfig config) {
        client = Client.builder()
                .apiKey(config.getApi().getGemini().getKey())
                .build();
    }

    public String getAdvice(Map<Coins, BigDecimal> coins) {
        return client.models.generateContent(
                AI_MODEL,
                ADVICE_PROMPT + coins.toString(),
                null
        ).text();
    }
}

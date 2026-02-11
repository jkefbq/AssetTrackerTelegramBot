package com.cryptodemoaccount;

import com.cryptodemoaccount.config.YamlConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.Duration;

@SpringBootApplication
@AllArgsConstructor
public class BotApplication {

    private static final Duration REST_TEMPLATE_CONNECTION_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration REST_TEMPLATE_READ_TIMEOUT = Duration.ofSeconds(3);

	public static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}

    @Bean
    public TelegramClient telegramClient(YamlConfig config) {
        return new OkHttpTelegramClient(config.getApi().getTelegram().getKey());
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(REST_TEMPLATE_CONNECTION_TIMEOUT);
        factory.setReadTimeout(REST_TEMPLATE_READ_TIMEOUT);
        return new RestTemplate(factory);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}

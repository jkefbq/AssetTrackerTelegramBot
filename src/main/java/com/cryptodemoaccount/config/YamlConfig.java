package com.cryptodemoaccount.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
@NoArgsConstructor
public class YamlConfig {

    private Admin admin;
    private Api api;

    @Data
    public static class Admin {
        private String password;
    }

    @Data
    public static class Api {
        private Gecko gecko;
        private Gemini gemini;
        private Telegram telegram;

        @Data
        public static class Gecko {

            private String key;
            private Headers headers;
            private Urls urls;

            @Data
            public static class Headers {
                private String apiKeyHeader;
            }

            @Data
            public static class Urls {
                private String simplePrice;
            }
        }

        @Data
        public static class Gemini {
            private String key;
        }

        @Data
        public static class Telegram {
            private String key;
        }
    }
}

package com.assettracker.main.telegram_bot;

import com.assettracker.main.telegram_bot.database.repository.BagRepository;
import com.assettracker.main.telegram_bot.database.repository.UserRepository;
import com.assettracker.main.telegram_bot.database.service.DataInitializerService;
import com.assettracker.main.telegram_bot.events.Button;
import com.assettracker.main.telegram_bot.events.ButtonEventHandler;
import com.assettracker.main.telegram_bot.events.MessageEventHandler;
import com.assettracker.main.telegram_bot.menu.bag_menu.BagMenu;
import com.assettracker.main.telegram_bot.menu.main_menu.MainMenu;
import com.assettracker.main.telegram_bot.menu.my_profile_menu.MyProfileMenu;
import com.assettracker.main.telegram_bot.menu.waiting_menu.WaitingMenu;
import com.assettracker.main.telegram_bot.service.AIService;
import com.assettracker.main.telegram_bot.service.ButtonHandler;
import com.assettracker.main.telegram_bot.service.MarketInfoKeeper;
import com.assettracker.main.telegram_bot.service.MessageHandler;
import com.assettracker.main.telegram_bot.service.UpdateConsumer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@Profile("test")
@SpringBootTest
@Transactional
@Testcontainers
class TgBotApplicationTests {

    public static final String PSQL_IMG = "postgres:18-alpine";
    public static final String REDIS_IMG = "redis:7-alpine";

    @MockitoSpyBean
    MessageHandler messageHandler;
    @MockitoSpyBean
    ButtonHandler buttonHandler;
    @MockitoSpyBean
    ButtonEventHandler buttonEventHandler;
    @MockitoSpyBean
    MessageEventHandler messageEventHandler;
    @MockitoSpyBean
    DataInitializerService initializerService;

    @MockitoBean
    MainMenu mainMenu;
    @MockitoBean
    BagMenu bagMenu;
    @MockitoBean
    MyProfileMenu profileMenu;
    @MockitoBean
    WaitingMenu waitingMenu;
    @MockitoBean
    MarketInfoKeeper marketInfoKeeper;
    @MockitoBean
    AIService aiService;

    @Autowired
    BagRepository bagRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UpdateConsumer updateConsumer;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>(PSQL_IMG)
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    @Container
    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>(REDIS_IMG)
                    .waitingFor(Wait.forListeningPort())
                    .withExposedPorts(6379);

    @DynamicPropertySource
    static void setPostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @DynamicPropertySource
    static void setRedis(
            DynamicPropertyRegistry registry
    ) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    public Update getUpdate_message() {
        var update = new Update();
        var message = new Message();
        update.setMessage(message);
        var user = new User(1L, "firstname", false);
        user.setLastName("lastname");
        user.setUserName("username");
        update.getMessage().setFrom(user);
        var chat = Mockito.mock(Chat.class);
        when(chat.getId()).thenReturn(1L);
        update.getMessage().setChat(chat);
        return update;
    }

    public Update getUpdate_button() {
        var update = new Update();
        var callbackQuery = new CallbackQuery();
        update.setCallbackQuery(callbackQuery);
        var user = new User(1L, "firstname", false);
        user.setLastName("lastname");
        user.setUserName("username");
        update.getCallbackQuery().setFrom(user);
        return update;
    }

    @BeforeEach
    public void cleanCache() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }

    @Test
    void simulationButtonPressing_myBag() {
        var update = getUpdate_button();
        update.getCallbackQuery().setData(Button.MY_BAG.getCallbackData());

        updateConsumer.consume(update);

        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(buttonHandler).handle(update.getCallbackQuery());
                    verify(buttonEventHandler).handleMyBag(any());
                    verify(bagMenu).editMsgAndSendMenu(any(), any());
                });
    }

    @Test
    void simulationButtonPressing_myProfile() {
        var update = getUpdate_button();
        update.getCallbackQuery().setData(Button.MY_PROFILE.getCallbackData());

        updateConsumer.consume(update);

        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(buttonHandler).handle(update.getCallbackQuery());
                    verify(buttonEventHandler).handleMyProfile(any());
                    verify(profileMenu).editMsgAndSendMenu(any(), any());
                });
    }

    @Test
    public void simulationSendCommand_start() {
        var update = getUpdate_message();
        update.getMessage().setText(com.assettracker.main.telegram_bot.events.Message.START.getText());

        updateConsumer.consume(update);

        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(messageHandler).handle(update);
                    verify(messageEventHandler).handleStart(any());
                    verify(initializerService).initializeUserAndBag(any());
                    verify(mainMenu).sendMenu(any());
                    assertEquals(1, bagRepository.count());
                    assertEquals(1, userRepository.count());
                });
    }

    @Test
    public void simulationSendCommand_bag() {
        var update = getUpdate_message();
        update.getMessage().setText(com.assettracker.main.telegram_bot.events.Message.BAG.getText());

        updateConsumer.consume(update);

        await().atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    verify(messageHandler).handle(update);
                    verify(messageEventHandler).handleBag(any());
                    verify(bagMenu).sendMenu(any());
                });
    }
}

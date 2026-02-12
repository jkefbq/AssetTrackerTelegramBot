package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.service.LastMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LastMessageServiceTest {

    LastMessageService lastMessageService;

    @BeforeEach
    public void setUp() {
        this.lastMessageService = new LastMessageService();
    }

    @Test
    public void setLastMessageTest() {
        Long chatId = ThreadLocalRandom.current().nextLong();
        Integer messageId = ThreadLocalRandom.current().nextInt();

        lastMessageService.setLastMessage(chatId, messageId);

        assertNotNull(lastMessageService.getLastMessage(chatId));
    }

    @Test
    public void getLastMessageTest_withoutSetValue() {
        Long randomChatId = ThreadLocalRandom.current().nextLong();

        Integer messageId = lastMessageService.getLastMessage(randomChatId);

        assertNull(messageId);
    }

    @Test
    public void getLastMessageTest_withSetValue() {
        Long chatId = ThreadLocalRandom.current().nextLong();
        Integer setMessageId = ThreadLocalRandom.current().nextInt();
        lastMessageService.setLastMessage(chatId, setMessageId);

        Integer getMessageId = lastMessageService.getLastMessage(chatId);

        assertEquals(setMessageId, getMessageId);
    }
    
}

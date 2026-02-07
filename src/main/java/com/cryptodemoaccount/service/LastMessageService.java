package com.cryptodemoaccount.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LastMessageService {

    private final ConcurrentHashMap<Long, Integer> lastMessage = new ConcurrentHashMap<>();

    public void setLastMessage(Long chatId, Integer messageId) {
        lastMessage.put(chatId, messageId);
    }

    public Integer getLastMessage(Long chatId) {
        return lastMessage.get(chatId);
    }
}

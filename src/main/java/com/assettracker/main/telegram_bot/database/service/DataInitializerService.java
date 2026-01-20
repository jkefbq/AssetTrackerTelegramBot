package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.entity.BagEntity;
import com.assettracker.main.telegram_bot.database.entity.UserEntity;
import com.assettracker.main.telegram_bot.service.ChatId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class DataInitializerService {

    private final UserDbService userService;
    private final BagService bagService;

    public void initializeUserAndBag(Update update) {
        UserEntity user = new UserEntity(update);
        BagDto bag = bagService.createBag(new BagEntity(ChatId.get(update)));
        user.setBag(bagService.toEntity(bag));
        userService.createUser(user);
    }
}

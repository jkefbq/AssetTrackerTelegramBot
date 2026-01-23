package com.assettracker.main.telegram_bot.database.service;

import com.assettracker.main.telegram_bot.database.dto.UserDto;
import com.assettracker.main.telegram_bot.database.entity.UserEntity;
import com.assettracker.main.telegram_bot.database.mapper.UserMapper;
import com.assettracker.main.telegram_bot.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private static final String CACHE_NAMES = "users";
    private final UserRepository userRepo;
    private final UserMapper mapper;

    public UserDto createUser(UserEntity entity) {
        return mapper.toDto(userRepo.save(entity));
    }

    @Cacheable(cacheNames = CACHE_NAMES, key = "#chatId", unless = "#result == null")
    public Optional<UserDto> findByChatId(Long chatId) {
        return userRepo.findByChatId(chatId).map(mapper::toDto);
    }

}

package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.UserEntity;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.mapper.UserMapper;
import com.cryptodemoaccount.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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

    public boolean isUserWriteQuestion(Long chatId) {
        return userRepo.findByChatId(chatId).orElseThrow().getStatus() == UserStatus.WRITING_QUESTION;
    }

    public void saveUser(UserDto dto) {
        var entity = mapper.toEntity(dto);
        userRepo.save(entity);
    }

    public Optional<UserDto> findById(UUID id) {
        return userRepo.findById(id).map(mapper::toDto);
    }

}

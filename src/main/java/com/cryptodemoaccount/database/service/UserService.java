package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.UserDto;
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
public class UserService implements CrudService<UserDto> {

    private static final String CACHE_NAMES = "users";
    private final UserRepository userRepo;
    private final UserMapper mapper;


    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        return mapper.toDto(
                userRepo.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CACHE_NAMES, key = "#chatId", unless = "#result == null")
    public Optional<UserDto> findByChatId(Long chatId) {
        return userRepo.findByChatId(chatId).map(mapper::toDto);
    }

    @Override
    @Transactional
    public UserDto update(UserDto dto) {
        return mapper.toDto(
                userRepo.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    @Transactional
    public void deleteByChatId(Long chatId) {
        userRepo.delete(
                findByChatId(chatId).map(mapper::toEntity).orElseThrow()
        );
    }

    @Transactional
    public Optional<UserDto> findById(UUID id) {
        return userRepo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public boolean isUserWriteQuestion(Long chatId) {
        return userRepo.findByChatId(chatId).orElseThrow().getStatus() == UserStatus.WRITING_QUESTION;
    }

}

package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.mapper.UserCoinMapper;
import com.cryptodemoaccount.database.repository.UserCoinRepository;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserCoinService implements CrudService<UserCoinDto> {

    private final UserCoinRepository userCoinRepository;
    private final UserCoinMapper mapper;

    @Override
    @Transactional
    public UserCoinDto create(UserCoinDto coin) {
        log.info("save tmp UserCoinDto={}", coin);
        return mapper.toDto(
                userCoinRepository.save(mapper.toEntity(coin))
        );
    }

    @Override
    @Transactional
    public Optional<UserCoinDto> findByChatId(Long chatId) {
        return userCoinRepository.findByChatId(chatId).map(mapper::toDto);
    }

    @Override
    @Transactional
    public UserCoinDto update(UserCoinDto coin) {
        log.info("save tmp UserCoinDto={}", coin);
        return mapper.toDto(
                userCoinRepository.save(mapper.toEntity(coin))
        );
    }

    @Override
    @Transactional
    public void deleteByChatId(Long chatId) {
        log.info("delete tmp UserCoinDto with chatId={}", chatId);
        userCoinRepository.deleteByChatId(chatId);
    }

    @Transactional
    public boolean isUserWaitingNumber(Long chatId) {
        return userCoinRepository.findByChatId(chatId).isPresent();
    }
}

package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.entity.UserCoinEntity;
import com.cryptodemoaccount.database.mapper.UserCoinMapperImpl;
import com.cryptodemoaccount.database.repository.UserCoinRepository;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCoinServiceTest {

    @Mock
    UserCoinRepository userCoinRepository;
    @Mock
    UserCoinMapperImpl mapper;

    @InjectMocks
    UserCoinService userCoinService;

    public UserCoinDto getUserCoin() {
        return new UserCoinDto(
                UUID.randomUUID(),
                ThreadLocalRandom.current().nextLong(),
                Coins.BITCOIN,
                BigDecimal.TEN,
                AssetDo.CREATE
        );
    }

    public UserCoinEntity getUserCoinEntity() {
        return new UserCoinEntity(getUserCoin());
    }

    @Test
    public void createTest() {
        var userCoin = getUserCoin();

        userCoinService.create(userCoin);

        verify(userCoinRepository, times(1)).save(any());
    }

    @Test
    public void updateTest() {
        var userCoin = getUserCoin();

        userCoinService.update(userCoin);

        verify(userCoinRepository, times(1)).save(any());
    }

    @Test
    public void findByChatIdTest() {
        UserCoinEntity entity = getUserCoinEntity();
        when(userCoinRepository.findByChatId(entity.getChatId())).thenReturn(Optional.of(entity));

        userCoinService.findByChatId(entity.getChatId());

        verify(userCoinRepository, times(1)).findByChatId(any());
    }

    @Test
    public void deleteByChatIdTest() {
        userCoinService.deleteByChatId(1L);

        verify(userCoinRepository, times(1)).deleteByChatId(any());
    }

    @Test
    public void isUserWaitingNumberTest() {
        when(userCoinRepository.findByChatId(1L)).thenReturn(Optional.of(getUserCoinEntity()));
        userCoinService.isUserWaitingNumber(1L);

        verify(userCoinRepository, times(1)).findByChatId(any());
    }
}


package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.entity.UserCoinEntity;
import com.cryptodemoaccount.database.repository.UserCoinRepository;
import com.cryptodemoaccount.menu.asset_list_menu.AssetDo;
import com.cryptodemoaccount.menu.asset_list_menu.Coins;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    @Mock
    UserCoinRepository userCoinRepository;

    @InjectMocks
    AssetService assetService;

    public UserCoin getUserCoin() {
        return new UserCoin(
                UUID.randomUUID(),
                Coins.BITCOIN,
                ThreadLocalRandom.current().nextLong(),
                AssetDo.CREATE
        );
    }

    public UserCoinEntity getUserCoinEntity() {
        return new UserCoinEntity(getUserCoin());
    }

    @Test
    public void saveTmpUserCoinTest() {
        var userCoin = getUserCoin();
        assetService.saveTmpUserCoin(userCoin);

        verify(userCoinRepository, times(1)).save(any());
    }

    @Test
    public void getTmpUserCoinTest() {
        when(userCoinRepository.findByChatId(1L)).thenReturn(Optional.of(getUserCoinEntity()));
        assetService.getTmpUserCoin(1L);

        verify(userCoinRepository, times(1)).findByChatId(any());
    }

    @Test
    public void deleteTmpUserCoinTest() {
        assetService.deleteTmpUserCoin(1L);

        verify(userCoinRepository, times(1)).deleteByChatId(any());
    }

    @Test
    public void isUserWaitingNumberTest() {
        when(userCoinRepository.findByChatId(1L)).thenReturn(Optional.of(getUserCoinEntity()));
        assetService.isUserWaitingNumber(1L);

        verify(userCoinRepository, times(1)).findByChatId(any());
    }
}


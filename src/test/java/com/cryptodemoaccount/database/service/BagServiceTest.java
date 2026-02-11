package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.mapper.BagMapperImpl;
import com.cryptodemoaccount.database.repository.BagRepository;
import com.cryptodemoaccount.service.MarketInfoKeeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BagServiceTest {

    @Mock
    BagRepository bagRepository;
    @Mock
    BagMapperImpl bagMapper;
    @Mock
    MarketInfoKeeper marketInfoKeeper;
    @InjectMocks
    BagService bagService;

    public BagDto getBagDto() {
        return BagDto.builder()
                .id(UUID.randomUUID())
                .chatId(ThreadLocalRandom.current().nextLong())
                .createdAt(LocalDate.now())
                .totalCost(BigDecimal.ZERO)
                .assets(new HashMap<>())
                .build();
    }

    @Test
    public void createTest() {
        var bag = getBagDto();

        bagService.create(bag);

        verify(bagRepository, times(1)).save(any());
    }

    @Test
    public void updateBagTest() {
        var bag = getBagDto();

        bagService.update(bag);

        verify(bagRepository, times(1)).save(any());
    }

    @Test
    public void findByChatIdTest() {
        bagService.findByChatId(1L);
        verify(bagRepository, times(1)).findByChatId(1L);
    }
}

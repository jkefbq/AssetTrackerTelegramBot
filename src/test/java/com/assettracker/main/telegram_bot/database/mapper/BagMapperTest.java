package com.assettracker.main.telegram_bot.database.mapper;

import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.entity.BagEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagMapperTest {

    BagMapper mapper = new BagMapperImpl();

    @Test
    public void toDtoTest() {
        BagEntity entity = new BagEntity(ThreadLocalRandom.current().nextLong());
        entity.setId(UUID.randomUUID());

        BagDto dto = mapper.toDto(entity);

        assertEquals(entity.getAssets(), dto.getAssets());
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getChatId(), dto.getChatId());
    }

    @Test
    public void toEntityTest() {
        BagDto dto = BagDto.builder()
                .id(UUID.randomUUID())
                .assets(new HashMap<>())
                .createdAt(LocalDate.now())
                .build();

        BagEntity entity = mapper.toEntity(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getAssets(), dto.getAssets());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
    }
}

package com.assettracker.main.telegram_bot.database.mapper;

import com.assettracker.main.telegram_bot.database.dto.BagDto;
import com.assettracker.main.telegram_bot.database.entity.BagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagMapper {
    BagDto toDto(BagEntity entity);
    BagEntity toEntity(BagDto dto);
}

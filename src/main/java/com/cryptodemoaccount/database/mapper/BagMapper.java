package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.entity.BagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BagMapper {
    BagDto toDto(BagEntity entity);
    BagEntity toEntity(BagDto dto);
}

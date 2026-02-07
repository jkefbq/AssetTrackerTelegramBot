package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity entity);
    UserEntity toEntity(UserDto dto);
}

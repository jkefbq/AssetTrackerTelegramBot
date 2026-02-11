package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.entity.UserCoinEntity;
import com.cryptodemoaccount.menu.asset_list_menu.UserCoinDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCoinMapper {
    UserCoinDto toDto(UserCoinEntity entity);
    UserCoinEntity toEntity(UserCoinDto coin);
}

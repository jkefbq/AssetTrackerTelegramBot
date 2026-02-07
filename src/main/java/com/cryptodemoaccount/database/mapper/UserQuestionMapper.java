package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.dto.UserQuestionDto;
import com.cryptodemoaccount.database.entity.UserQuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserQuestionMapper {
    UserQuestionEntity toEntity(UserQuestionDto dto);
    UserQuestionDto toDto(UserQuestionEntity entity);
}

package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.dto.UpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Update;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateUtils.getUser(update).getFirstName())",
            target = "firstName")
    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateUtils.getUser(update).getLastName())",
            target = "lastName")
    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateUtils.getUser(update).getUserName())",
            target = "userName")
    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateUtils.getChatId(update))",
            target = "chatId")
    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateUtils.getUserInput(update))",
            target = "userInput")
    UpdateDto toDto(Update update);
}

package com.cryptodemoaccount.database.mapper;

import com.cryptodemoaccount.database.dto.UpdateDto;
import com.cryptodemoaccount.service.UpdateMappingHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

//    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateMappingHelper.getUser(update).getFirstName())",
//            target = "firstName")
//    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateMappingHelper.getUser(update).getLastName())",
//            target = "lastName")
//    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateMappingHelper.getUser(update).getUserName())",
//            target = "userName")
//    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateMappingHelper.getChatId(update))",
//            target = "chatId")
//    @Mapping(expression = "java(com.cryptodemoaccount.service.UpdateMappingHelper.getUserInput(update))",
//            target = "userInput")

    @Mapping(source = "update", target = "firstName", qualifiedByName = "getFirstName")
    @Mapping(source = "update", target = "lastName", qualifiedByName = "getLastName")
    @Mapping(source = "update", target = "userName", qualifiedByName = "getUserName")
    @Mapping(source = "update", target = "chatId", qualifiedByName = "getChatId")
    @Mapping(source = "update", target = "userInput", qualifiedByName = "getUserInput")
    UpdateDto toDto(Update update);

    @Named("getFirstName")
    default String mapFirstName(Update update) {
        return UpdateMappingHelper.getUser(update).getFirstName();
    }

    @Named("getLastName")
    default String mapLastName(Update update) {
        return UpdateMappingHelper.getUser(update).getLastName();
    }

    @Named("getUserName")
    default String mapUserName(Update update) {
        return UpdateMappingHelper.getUser(update).getUserName();
    }

    @Named("getChatId")
    default Long mapChatId(Update update) {
        return UpdateMappingHelper.getChatId(update);
    }

    @Named("getUserInput")
    default Optional<String> mapUserInput(Update update) {
        return UpdateMappingHelper.getUserInput(update);
    }
}

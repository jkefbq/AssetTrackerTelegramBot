package com.cryptodemoaccount.database.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class UpdateDto {
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private Optional<String> userInput;
}

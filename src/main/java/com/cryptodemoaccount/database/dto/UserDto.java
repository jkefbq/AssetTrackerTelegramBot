package com.cryptodemoaccount.database.dto;

import com.cryptodemoaccount.database.entity.BagEntity;
import com.cryptodemoaccount.database.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private BagEntity bag;
    private UserStatus status;
}

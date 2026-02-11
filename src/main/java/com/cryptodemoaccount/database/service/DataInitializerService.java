package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.dto.UpdateDto;
import com.cryptodemoaccount.database.dto.UserDto;
import com.cryptodemoaccount.database.entity.UserStatus;
import com.cryptodemoaccount.database.mapper.BagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class DataInitializerService {

    private final UserService userService;
    private final BagService bagService;
    private final BagMapper bagMapper;

    @Transactional
    public void initializeUserAndBag(UpdateDto updateDto) {
        UserDto user = getNewUserDto(updateDto);
        BagDto oldBag = getNewBagDto(updateDto);

        BagDto newBag = bagService.create(oldBag);
        user.setBag(bagMapper.toEntity(newBag));
        userService.create(user);
    }

    private UserDto getNewUserDto(UpdateDto updateDto) {
        return UserDto.builder()
                .status(UserStatus.FREE)
                .userName(updateDto.getUserName())
                .firstName(updateDto.getFirstName())
                .lastName(updateDto.getLastName())
                .chatId(updateDto.getChatId())
                .build();
    }

    private BagDto getNewBagDto(UpdateDto updateDto) {
        return BagDto.builder()
                .chatId(updateDto.getChatId())
                .assets(new HashMap<>())
                .createdAt(LocalDate.now())
                .totalCost(BigDecimal.ZERO)
                .assetCount(0)
                .build();
    }
}

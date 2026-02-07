package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.UpdateDto;
import com.cryptodemoaccount.database.dto.BagDto;
import com.cryptodemoaccount.database.entity.BagEntity;
import com.cryptodemoaccount.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DataInitializerService {

    private final UserService userService;
    private final BagService bagService;

    @Transactional
    public void initializeUserAndBag(UpdateDto updateDto) {
        UserEntity user = new UserEntity(updateDto);
        BagDto bag = bagService.createBag(new BagEntity(updateDto.getChatId()));
        user.setBag(bagService.toEntity(bag));
        userService.createUser(user);
    }
}

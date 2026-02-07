package com.cryptodemoaccount.database.repository;

import com.cryptodemoaccount.database.entity.UserCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCoinRepository extends JpaRepository<UserCoinEntity, UUID> {
    Optional<UserCoinEntity> findByChatId(Long chatId);
    void deleteByChatId(Long chatId);
}

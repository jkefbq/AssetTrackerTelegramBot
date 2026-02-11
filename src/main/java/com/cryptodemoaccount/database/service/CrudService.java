package com.cryptodemoaccount.database.service;

import java.util.Optional;

public interface CrudService<T> {
    T create(T t);
    Optional<T> findByChatId(Long chatId);
    T update(T t);
    void deleteByChatId(Long chatId);
}

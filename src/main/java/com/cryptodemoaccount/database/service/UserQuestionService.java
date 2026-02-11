package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.database.dto.UserQuestionDto;
import com.cryptodemoaccount.database.mapper.UserQuestionMapper;
import com.cryptodemoaccount.database.repository.UserQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserQuestionService {

    private final UserQuestionRepository userQuestionRepository;
    private final UserQuestionMapper mapper;

    @Transactional
    public UserQuestionDto create(UserQuestionDto dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(
                userQuestionRepository.save(entity)
        );
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserQuestionDto> getAllUserQuestions() {
        return userQuestionRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<UserQuestionDto> findById(UUID id) {
        return userQuestionRepository.findById(id).map(mapper::toDto);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(UUID id) {
        userQuestionRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserQuestionDto> findAllByUserId(UUID userId) {
        return userQuestionRepository.findByUserId(userId).stream()
                .map(mapper::toDto)
                .toList();
    }
}

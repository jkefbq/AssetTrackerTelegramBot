package com.cryptodemoaccount.database.service;

import com.cryptodemoaccount.config.YamlConfig;
import com.cryptodemoaccount.config.security.AdminUserService;
import com.cryptodemoaccount.config.security.SecurityConfig;
import com.cryptodemoaccount.database.dto.UserQuestionDto;
import com.cryptodemoaccount.database.mapper.UserQuestionMapperImpl;
import com.cryptodemoaccount.database.repository.UserQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({SecurityConfig.class, AdminUserService.class, BCryptPasswordEncoder.class,
        UserQuestionMapperImpl.class, UserQuestionService.class})
public class UserQuestionServiceTest {

    @Autowired
    UserQuestionService questionService;

    @MockitoBean
    UserQuestionRepository questionRepository;

    @MockitoBean(answers = Answers.RETURNS_MOCKS)
    YamlConfig config;

    @Test
    public void createTest() {
        questionService.create(new UserQuestionDto());

        verify(questionRepository, times(1)).save(any());
    }

    @Test
    public void findAllByUserIdTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.findAllByUserId(UUID.randomUUID()));
        verify(questionRepository, never()).findByUserId(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findAllByUserIdTest_admin() {
        questionService.findAllByUserId(UUID.randomUUID());

        verify(questionRepository, times(1)).findByUserId(any());
    }

    @Test
    public void getAllUserQuestionsTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.getAllUserQuestions());
        verify(questionRepository, never()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUserQuestionsTest_admin() {
        questionService.getAllUserQuestions();

        verify(questionRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTestTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.findById(UUID.randomUUID()));
        verify(questionRepository, never()).findById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void findByIdTestTest_admin() {
        questionService.findById(UUID.randomUUID());

        verify(questionRepository, times(1)).findById(any());
    }

    @Test
    public void deleteByIdTest_unauthorize() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> questionService.deleteById(UUID.randomUUID()));
        verify(questionRepository, never()).deleteById(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteByIdTest_admin() {
        questionService.deleteById(UUID.randomUUID());
        verify(questionRepository, times(1)).deleteById(any());
    }
}

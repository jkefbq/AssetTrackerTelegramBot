package com.cryptodemoaccount.service;

import com.google.genai.Client;
import com.google.genai.Models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AIServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    Client client;

    @Mock
    Models models;

    @InjectMocks
    AIService aiService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(client, "models", models);
    }

    @Test
    public void getAdviceTest() {
        try {
            aiService.getAdvice(new HashMap<>());
        } catch (Exception ignored) {}

        verify(models).generateContent(any(), anyString(), any());
    }
}

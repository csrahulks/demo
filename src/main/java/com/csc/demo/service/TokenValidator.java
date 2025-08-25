package com.csc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csc.demo.repository.TokenRepository;

import java.time.LocalDateTime;

@Service
public class TokenValidator {

    @Autowired
    private TokenRepository tokenRepository;

    public boolean validate(String clientToken) {
        if (clientToken == null || clientToken.trim().isEmpty()) {
            return false;
        }

        return tokenRepository.findByValue(clientToken)
                .filter(token -> token.isValid() && token.getExpiryDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}

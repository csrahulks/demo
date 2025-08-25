package com.csc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csc.demo.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);
}

package com.dorovidal.userservice.service;

import com.dorovidal.userservice.dto.TransactionRequestDto;
import com.dorovidal.userservice.dto.TransactionResponseDto;
import com.dorovidal.userservice.entity.UserTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionResponseDto> create(final TransactionRequestDto requestDto);

    Flux<UserTransaction> getByUserId(Long userId);
}

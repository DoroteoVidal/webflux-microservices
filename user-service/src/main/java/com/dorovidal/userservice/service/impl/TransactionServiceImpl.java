package com.dorovidal.userservice.service.impl;

import com.dorovidal.userservice.dto.TransactionRequestDto;
import com.dorovidal.userservice.dto.TransactionResponseDto;
import com.dorovidal.userservice.dto.TransactionStatus;
import com.dorovidal.userservice.entity.UserTransaction;
import com.dorovidal.userservice.repository.UserRepository;
import com.dorovidal.userservice.repository.UserTransactionRepository;
import com.dorovidal.userservice.service.TransactionService;
import com.dorovidal.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    @Override
    public Mono<TransactionResponseDto> create(final TransactionRequestDto requestDto) {
        return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(transactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    @Override
    public Flux<UserTransaction> getByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}

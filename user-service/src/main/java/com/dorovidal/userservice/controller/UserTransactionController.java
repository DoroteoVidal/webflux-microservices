package com.dorovidal.userservice.controller;

import com.dorovidal.userservice.dto.TransactionRequestDto;
import com.dorovidal.userservice.dto.TransactionResponseDto;
import com.dorovidal.userservice.entity.UserTransaction;
import com.dorovidal.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> create(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(transactionService::create);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") Long userId) {
        return transactionService.getByUserId(userId);
    }
}

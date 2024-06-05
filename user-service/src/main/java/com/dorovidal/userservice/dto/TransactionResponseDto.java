package com.dorovidal.userservice.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {

    private Long userId;
    private Integer amount;
    private TransactionStatus status;
}

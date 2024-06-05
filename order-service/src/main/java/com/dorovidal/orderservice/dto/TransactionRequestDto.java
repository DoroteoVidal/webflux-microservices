package com.dorovidal.orderservice.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {

    private Long userId;
    private Integer amount;
}

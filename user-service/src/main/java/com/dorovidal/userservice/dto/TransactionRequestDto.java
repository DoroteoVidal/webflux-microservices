package com.dorovidal.userservice.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {

    private Long userId;
    private Integer amount;
}

package com.dorovidal.orderservice.dto;

import lombok.Data;

@Data
public class PurchaseOrderRequestDto {

    private Long userId;
    private String productId;
}

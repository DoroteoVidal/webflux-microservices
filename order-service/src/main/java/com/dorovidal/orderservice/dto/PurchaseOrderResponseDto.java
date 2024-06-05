package com.dorovidal.orderservice.dto;

import lombok.Data;

@Data
public class PurchaseOrderResponseDto {

    private Long orderId;
    private Long userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}

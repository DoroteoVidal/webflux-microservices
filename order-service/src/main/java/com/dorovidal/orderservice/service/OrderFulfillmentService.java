package com.dorovidal.orderservice.service;

import com.dorovidal.orderservice.dto.PurchaseOrderRequestDto;
import com.dorovidal.orderservice.dto.PurchaseOrderResponseDto;
import reactor.core.publisher.Mono;

public interface OrderFulfillmentService {

    Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono);
}

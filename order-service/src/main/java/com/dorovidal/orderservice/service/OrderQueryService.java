package com.dorovidal.orderservice.service;

import com.dorovidal.orderservice.dto.PurchaseOrderResponseDto;
import reactor.core.publisher.Flux;

public interface OrderQueryService {

    Flux<PurchaseOrderResponseDto> getProductsByUserId(Long userId);
}

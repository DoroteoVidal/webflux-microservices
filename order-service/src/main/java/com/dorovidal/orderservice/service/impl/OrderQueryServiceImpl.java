package com.dorovidal.orderservice.service.impl;

import com.dorovidal.orderservice.dto.PurchaseOrderResponseDto;
import com.dorovidal.orderservice.entity.PurchaseOrder;
import com.dorovidal.orderservice.repository.PurchaseOrderRepository;
import com.dorovidal.orderservice.service.OrderQueryService;
import com.dorovidal.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Override
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(Long userId) {
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream()) // bloking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}

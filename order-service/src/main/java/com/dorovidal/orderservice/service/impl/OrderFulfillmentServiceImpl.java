package com.dorovidal.orderservice.service.impl;

import com.dorovidal.orderservice.client.ProductClient;
import com.dorovidal.orderservice.client.UserClient;
import com.dorovidal.orderservice.dto.PurchaseOrderRequestDto;
import com.dorovidal.orderservice.dto.PurchaseOrderResponseDto;
import com.dorovidal.orderservice.dto.RequestContext;
import com.dorovidal.orderservice.repository.PurchaseOrderRepository;
import com.dorovidal.orderservice.service.OrderFulfillmentService;
import com.dorovidal.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(purchaseOrderRepository::save) // blocking ya que NO retorna Mono
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)

                // Como el metodo save de jpa no es reactivo, utilizamos los Schedulers para
                // obtener un comportamiento asincrono y no bloquee el funcionamiento de la pipeline.
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }

}

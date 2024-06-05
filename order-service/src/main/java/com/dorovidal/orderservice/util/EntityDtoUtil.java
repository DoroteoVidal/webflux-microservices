package com.dorovidal.orderservice.util;

import com.dorovidal.orderservice.dto.*;
import com.dorovidal.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());

        return dto;
    }

    public static void setTransactionRequestDto(RequestContext rc) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(rc.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(rc.getProductDto().getPrice());
        rc.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext rc) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(rc.getTransactionRequestDto().getUserId());
        purchaseOrder.setProductId(rc.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(rc.getProductDto().getPrice());

        TransactionStatus status = rc.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);

        return purchaseOrder;
    }
}

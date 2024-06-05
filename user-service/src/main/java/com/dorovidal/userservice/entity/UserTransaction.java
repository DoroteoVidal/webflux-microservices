package com.dorovidal.userservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class UserTransaction {

    @Id
    private Long id;
    private Long userId;
    private Integer amount;
    private LocalDateTime transactionDate;
}

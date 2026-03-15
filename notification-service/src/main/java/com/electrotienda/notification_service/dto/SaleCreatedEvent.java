package com.electrotienda.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCreatedEvent {
    private Long saleId;
    private Long cartId;
    private Double totalAmount;
    private String customerEmail;
}

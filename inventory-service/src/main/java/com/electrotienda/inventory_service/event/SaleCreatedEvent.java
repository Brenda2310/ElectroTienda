package com.electrotienda.inventory_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreatedEvent {
    private Long saleId;
    private Long cartId;
    private Double totalAmount;
    private String customerEmail;
}

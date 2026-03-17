package com.electrotienda.inventory_service.consumer;

import com.electrotienda.inventory_service.client.CartClient;
import com.electrotienda.inventory_service.dto.CartItemDTO;
import com.electrotienda.inventory_service.event.SaleCreatedEvent;
import com.electrotienda.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryConsumer {
    private final IInventoryService service;
    private final CartClient client;

    @KafkaListener(topics = "notification-topic", groupId = "inventory-group")
    public void consumeSaleEvent(SaleCreatedEvent event) {
        log.info("📥 Evento recibido en Inventario para la Venta ID: {}", event.getSaleId());

        try {
            var cart = client.getCartById(event.getCartId());

            for (CartItemDTO item : cart.getItems()) {
                service.deductStock(item.getProductId(), item.getQuantity());
            }

            log.info("📦 Proceso de inventario finalizado para el carrito: {}", event.getCartId());

        } catch (Exception e) {
            log.error("❌ Error procesando el inventario: {}", e.getMessage());
        }
    }
}


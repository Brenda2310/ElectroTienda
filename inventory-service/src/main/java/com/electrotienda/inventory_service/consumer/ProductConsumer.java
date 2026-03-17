package com.electrotienda.inventory_service.consumer;

import com.electrotienda.inventory_service.event.ProductCreatedEvent;
import com.electrotienda.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {
    private final IInventoryService service;

    @KafkaListener(topics = "product-topic", groupId = "inventory-group")
    public void consumeProductCreated(ProductCreatedEvent event) {
        log.info("📥 Evento recibido de Kafka: Inicializando stock para Producto ID: {}", event.getProductId());

        try {
            service.initializeStock(event.getProductId(), event.getInitialStock());
        } catch (Exception e) {
            log.error("❌ Error al inicializar el stock: {}", e.getMessage());
        }
    }
}

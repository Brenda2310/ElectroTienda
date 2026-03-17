package com.electrotienda.inventory_service.consumer;

import com.electrotienda.inventory_service.client.CartClient;
import com.electrotienda.inventory_service.dto.CartItemDTO;
import com.electrotienda.inventory_service.event.ProductCreatedEvent;
import com.electrotienda.inventory_service.event.SaleCreatedEvent;
import com.electrotienda.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(
        topics = "inventory-topic",
        groupId = "inventory-group-v1"
)
public class InventoryConsumer {
    private final IInventoryService service;
    private final CartClient client;

    @KafkaHandler
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

    @KafkaHandler
    public void consumeProductCreated(ProductCreatedEvent event) {
        log.info("📥 Evento recibido de Kafka: Inicializando stock para Producto ID: {}", event.getProductId());

        try {
            service.initializeStock(event.getProductId(), event.getInitialStock());
        } catch (Exception e) {
            log.error("❌ Error al inicializar el stock: {}", e.getMessage());
        }
    }

    @KafkaHandler(isDefault = true)
    public void unknownEvent(Object event) {
        log.warn("❓ [DESCONOCIDO] Se recibió un evento que no sé procesar: {}", event.getClass().getName());
    }
}


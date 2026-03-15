package com.electrotienda.notification_service.consumer;

import com.electrotienda.notification_service.dto.SaleCreatedEvent;
import com.electrotienda.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final EmailService emailService;

    public void consumeSaleEvent(SaleCreatedEvent event){
        log.info("Evento recibido desde Kafka. Procesando notificación para Venta ID: {}", event.getSaleId());

        emailService.sendReceipt(event.getCustomerEmail(), event.getTotalAmount());
    }
}

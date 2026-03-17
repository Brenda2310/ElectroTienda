package com.electrotienda.sales_service.service;

import com.electrotienda.sales_service.client.CartClient;
import com.electrotienda.sales_service.dto.CartDTO;
import com.electrotienda.sales_service.entities.Sale;
import com.electrotienda.sales_service.event.SaleCreatedEvent;
import com.electrotienda.sales_service.repository.SaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements ISaleService{
    private final SaleRepository repository;
    private final CartClient client;
    private final KafkaTemplate<String, SaleCreatedEvent> kafkaTemplate;
    @Autowired
    @Lazy
    private ISaleService self;


    @Override
    public Sale createSale(Long cartId, String customerEmail) {
        CartDTO cart = self.getProtectedCart(cartId);

        if(cart.getTotalPrice() == null || cart.getTotalPrice() == 0.0){
            throw new RuntimeException("The sale could not be completed: Empty cart or Service not available.");
        }

        Sale sale = Sale.builder()
                .date(LocalDate.now())
                .cartId(cartId)
                .build();

        Sale saved = repository.save(sale);

        SaleCreatedEvent event = SaleCreatedEvent.builder()
                .customerEmail(customerEmail)
                .saleId(saved.getId())
                .cartId(saved.getCartId())
                .totalAmount(cart.getTotalPrice())
                .build();

        kafkaTemplate.send("inventory-topic", event);

        return saved;
    }

    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "fallbackGetCart")
    public CartDTO getProtectedCart(Long cartId) {
        return client.getCartById(cartId);
    }

    @Override
    public CartDTO fallbackGetCart(Long cartId, Throwable throwable) {
        log.error("Failed to communicate with cart-service. Enabling Fallback for product ID: {}", cartId);
        return CartDTO.builder()
                .id(cartId)
                .totalPrice(0.0)
                .build();
    }
}

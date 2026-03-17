package com.electrotienda.inventory_service.service;

import com.electrotienda.inventory_service.entities.Inventory;
import com.electrotienda.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService implements IInventoryService{
    private final InventoryRepository repository;

    @Override
    public boolean checkStock(Long productId, Integer quantity) {
        return repository.findByProductId(productId)
                .map(inv-> inv.getStock() > quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public void deductStock(Long productId, Integer quantity) {
        repository.findByProductId(productId)
                .ifPresent(inv-> {
                    if(inv.getStock() >= quantity){
                        inv.setStock(inv.getStock() - quantity);
                        repository.save(inv);
                        log.info("✅ Stock actualizado para el producto {}. Nuevo stock: {}", productId, inv.getStock());
                    }
                    else {
                        log.warn("⚠️ Stock insuficiente para el producto {}", productId);
                    }
                });
    }

    @Override
    @Transactional
    public void initializeStock(Long productId, Integer initialStock) {
        Inventory inventory = Inventory.builder()
                .productId(productId)
                .stock(Optional.ofNullable(initialStock).orElse(0))
                .build();
        repository.save(inventory);
        System.out.println("✅ Stock inicializado para el producto " + productId + ". Cantidad: " + initialStock);
    }
}

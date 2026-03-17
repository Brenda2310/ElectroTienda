package com.electrotienda.inventory_service.service;

public interface IInventoryService {
    boolean checkStock(Long productId, Integer quantity);
    void deductStock(Long productId, Integer quantity);
    void initializeStock(Long productId, Integer initialStock);
}

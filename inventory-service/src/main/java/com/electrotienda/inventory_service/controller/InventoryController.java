package com.electrotienda.inventory_service.controller;

import com.electrotienda.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final IInventoryService service;

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStock(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        boolean isAvailable = service.checkStock(productId, quantity);
        return ResponseEntity.ok(isAvailable);
    }
}

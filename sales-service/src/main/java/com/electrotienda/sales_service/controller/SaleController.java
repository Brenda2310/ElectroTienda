package com.electrotienda.sales_service.controller;

import com.electrotienda.sales_service.entities.Sale;
import com.electrotienda.sales_service.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final ISaleService service;

    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestParam Long cartId,
                                           @RequestParam String customerEmail){
        Sale newSale = service.createSale(cartId, customerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }
}

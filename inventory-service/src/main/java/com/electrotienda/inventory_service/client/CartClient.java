package com.electrotienda.inventory_service.client;

import com.electrotienda.inventory_service.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/cart/{id}")
    CartDTO getCartById(@PathVariable("id") Long id);
}

package com.electrotienda.cart_service.controller;

import com.electrotienda.cart_service.dto.CartDTO;
import com.electrotienda.cart_service.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ICartService service;

    @PostMapping
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
      CartDTO updatedCart = service.addProductToCart(cartId, productId, quantity);
      return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id){
        return ResponseEntity.ok(service.getCartById(id));
    }
}

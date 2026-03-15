package com.electrotienda.cart_service.controller;

import com.electrotienda.cart_service.dto.CartDTO;
import com.electrotienda.cart_service.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ICartService service;

    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
      CartDTO updatedCart = service.addProductToCart(cartId, productId, quantity);
      return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }
}

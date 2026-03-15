package com.electrotienda.cart_service.service;

import com.electrotienda.cart_service.dto.CartDTO;
import com.electrotienda.cart_service.dto.ProductDTO;

public interface ICartService {
    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);
    ProductDTO getProtectedProduct(Long productId);
    ProductDTO fallbackGetProduct(Long productId, Throwable throwable);
}

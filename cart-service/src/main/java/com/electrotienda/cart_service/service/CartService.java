package com.electrotienda.cart_service.service;

import com.electrotienda.cart_service.client.ProductClient;
import com.electrotienda.cart_service.dto.CartDTO;
import com.electrotienda.cart_service.dto.CartItemDTO;
import com.electrotienda.cart_service.dto.ProductDTO;
import com.electrotienda.cart_service.entities.Cart;
import com.electrotienda.cart_service.entities.CartItem;
import com.electrotienda.cart_service.repository.CartRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService implements ICartService{

    private final CartRepository repository;
    private final ProductClient client;
    @Autowired
    @Lazy
    private ICartService self;

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = repository.findById(cartId).orElse(new Cart());

        ProductDTO productDTO = self.getProtectedProduct(productId);

        if(productDTO.getPrice() == null || productDTO.getPrice() == 0.0){
            throw new RuntimeException("The product cannot be added: Product Service is unavailable.");
        }

        CartItem cartItem = CartItem.builder()
                .productId(productDTO.getId())
                .quantity(quantity)
                .unitPrice(productDTO.getPrice())
                .subTotal(quantity * productDTO.getPrice())
                .cart(cart)
                .build();

        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum());

        Cart savedCart = repository.save(cart);

        return mapToCartDTO(savedCart);
    }

    @Override
    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackGetProduct")
    public ProductDTO getProtectedProduct(Long productId) {
        return client.getProductById(productId);
    }

    @Override
    public ProductDTO fallbackGetProduct(Long productId, Throwable throwable) {
        log.error("Failed to communicate with product-service. Enabling Fallback for product ID: {}", productId);
        return ProductDTO.builder()
                .id(productId)
                .name("Product unavailable")
                .price(0.0)
                .build();
    }

    @Override
    public CartDTO getCartById(Long id) {
        Cart cart = repository.findById(id).orElseThrow(()-> new NoSuchElementException("Cart not found"));
        return mapToCartDTO(cart);
    }

    private CartDTO mapToCartDTO(Cart cart){
        return CartDTO.builder()
                .id(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .items(cart.getItems()
                        .stream().map(this::mapToCartItemDTO).collect(Collectors.toList()))
                .build();
    }

    private CartItemDTO mapToCartItemDTO(CartItem cartItem){
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .subTotal(cartItem.getSubTotal())
                .unitPrice(cartItem.getUnitPrice())
                .build();
    }
}

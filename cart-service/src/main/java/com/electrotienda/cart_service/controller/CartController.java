package com.electrotienda.cart_service.controller;

import com.electrotienda.cart_service.dto.CartDTO;
import com.electrotienda.cart_service.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart Controller", description = "Operations related to shopping cart")
public class CartController {
    private final ICartService service;

    @Operation(
            summary = "Add product to cart",
            description = "Adds a product with a specific quantity to a given cart"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added successfully"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add/{cartId}")
    public ResponseEntity<CartDTO> addProductToCart(
            @Parameter(description = "ID of the cart", example = "1")
            @PathVariable Long cartId,

            @Parameter(description = "ID of the product", example = "10")
            @RequestParam Long productId,

            @Parameter(description = "Quantity of the product", example = "2")
            @RequestParam Integer quantity
    ){
      CartDTO updatedCart = service.addProductToCart(cartId, productId, quantity);
      return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart);
    }

    @Operation(
            summary = "Get cart by ID",
            description = "Returns a cart based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart found"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(
            @Parameter(description = "ID of the cart", example = "1")
            @PathVariable Long id){
        return ResponseEntity.ok(service.getCartById(id));
    }
}

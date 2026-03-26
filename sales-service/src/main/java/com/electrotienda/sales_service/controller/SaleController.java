package com.electrotienda.sales_service.controller;

import com.electrotienda.sales_service.entities.Sale;
import com.electrotienda.sales_service.service.ISaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sale Controller", description = "Operations related to sales and checkout process")
public class SaleController {
    private final ISaleService service;

    @Operation(
            summary = "Create a sale",
            description = "Creates a sale from a cart and a customer email (checkout process)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale created successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Stock not available")
    })
    @PostMapping
    public ResponseEntity<Sale> createSale(
            @Parameter(description = "ID of the cart to checkout", example = "1")
            @RequestParam Long cartId,

            @Parameter(description = "Customer email", example = "user@example.com")
            @RequestParam String customerEmail){
        Sale newSale = service.createSale(cartId, customerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }
}

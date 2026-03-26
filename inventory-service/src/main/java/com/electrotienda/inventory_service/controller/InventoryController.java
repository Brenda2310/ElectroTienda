package com.electrotienda.inventory_service.controller;

import com.electrotienda.inventory_service.service.IInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Controller", description = "Operations related to inventory and stock validation")
public class InventoryController {
    private final IInventoryService service;

    @Operation(
            summary = "Check product stock",
            description = "Validates if there is enough stock for a given product and quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock check completed"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStock(
            @Parameter(description = "ID of the product", example = "10")
            @RequestParam Long productId,

            @Parameter(description = "Requested quantity", example = "3")
            @RequestParam Integer quantity) {

        boolean isAvailable = service.checkStock(productId, quantity);
        return ResponseEntity.ok(isAvailable);
    }
}

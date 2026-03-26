package com.electrotienda.product_service.controller;

import com.electrotienda.product_service.dto.ProductDTO;
import com.electrotienda.product_service.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Operations related to product management")
public class ProductController{
    private final IProductService service;

    @Operation(
            summary = "Get all products",
            description = "Returns a list of all available products"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(service.getAllProducts());
    }

    @Operation(
            summary = "Get product by ID",
            description = "Returns a product based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product", example = "1")
            @PathVariable("id") Long id){
        return ResponseEntity.ok(service.getProductById(id));
    }

    @Operation(
            summary = "Create a new product",
            description = "Creates a product and initializes its stock"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product information",
                    required = true
            )
            @RequestBody ProductDTO product,

            @Parameter(description = "Initial stock quantity", example = "50")
            @RequestParam Integer initialStock){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(product, initialStock));
    }
}

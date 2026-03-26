package com.electrotienda.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Product data transfer object")
public class ProductDTO {
    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product code", example = "PROD-1234")
    private String code;

    @Schema(description = "Product name", example = "Laptop")
    private String name;

    @Schema(description = "Product brand", example = "Samsung")
    private String brand;

    @Schema(description = "Product price", example = "1200.50")
    private Double price;
}

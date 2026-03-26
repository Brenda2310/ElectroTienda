package com.electrotienda.cart_service.dto;

import com.electrotienda.cart_service.entities.Cart;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Item inside the shopping cart")
public class CartItemDTO {
    @Schema(description = "Cart item ID", example = "10")
    private Long id;

    @Schema(description = "Product ID", example = "5")
    private Long productId;

    @Schema(description = "Product name", example = "Laptop")
    private String productName;

    @Schema(description = "Quantity of the product", example = "2")
    private Integer quantity;

    @Schema(description = "Price per unit", example = "1200.50")
    private Double unitPrice;

    @Schema(description = "Subtotal (quantity * unit price)", example = "2401.00")
    private Double subTotal;
}

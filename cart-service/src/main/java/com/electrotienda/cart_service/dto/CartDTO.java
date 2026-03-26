package com.electrotienda.cart_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Shopping cart data")
public class CartDTO {
    @Schema(description = "Cart ID", example = "1")
    private Long id;

    @Schema(description = "Total price of the cart", example = "2500.75")
    private Double totalPrice;

    @Schema(description = "List of items in the cart")
    private List<CartItemDTO> items;
}

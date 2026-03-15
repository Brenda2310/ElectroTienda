package com.electrotienda.sales_service.service;

import com.electrotienda.sales_service.dto.CartDTO;
import com.electrotienda.sales_service.entities.Sale;

public interface ISaleService {
    Sale createSale(Long cartId, String customerEmail);
    CartDTO getProtectedCart(Long cartId);
    CartDTO fallbackGetCart(Long cartId, Throwable throwable);
}

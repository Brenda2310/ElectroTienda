package com.electrotienda.product_service.service;

import com.electrotienda.product_service.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO product, Integer initialStock);
}

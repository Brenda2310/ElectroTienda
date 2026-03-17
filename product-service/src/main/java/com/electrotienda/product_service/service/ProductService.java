package com.electrotienda.product_service.service;

import com.electrotienda.product_service.dto.ProductDTO;
import com.electrotienda.product_service.entity.ProductEntity;
import com.electrotienda.product_service.event.ProductCreatedEvent;
import com.electrotienda.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository repository;
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    @Override
    public List<ProductDTO> getAllProducts() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        ProductEntity product = repository.findById(id).orElseThrow(()->
                new NoSuchElementException("Product not found"));
        return mapToDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, Integer initialStock) {
        ProductEntity product = ProductEntity.builder()
                .id(productDTO.getId())
                .price(productDTO.getPrice())
                .brand(productDTO.getBrand())
                .name(productDTO.getName())
                .code(productDTO.getCode())
                .build();

        ProductEntity savedProduct = repository.save(product);

        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(product.getId())
                .initialStock(initialStock)
                .build();

        System.out.println("Enviando evento a Kafka: Producto "+ savedProduct.getId() +"creado con stock " + initialStock);
        kafkaTemplate.send("product-topic", event);

        return mapToDTO(savedProduct);
    }

    private ProductDTO mapToDTO(ProductEntity product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .code(product.getCode())
                .price(product.getPrice())
                .build();
    }
}

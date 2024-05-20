package com.sebadev.ecommerce.service;

import com.sebadev.ecommerce.dto.ProductDto;
import com.sebadev.ecommerce.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(long id);

    ProductDto updateProduct(ProductDto productDto, long id);

    void deleteProductById(long id);

    ProductResponse getProductByCategory(long id, int pageNo, int pageSize);

    ProductResponse getProductByName(String name, int pageNo, int pageSize);
}

package com.sebadev.ecommerce.service;

import com.sebadev.ecommerce.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDto> getAllCategories();
}

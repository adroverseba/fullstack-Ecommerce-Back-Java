package com.sebadev.ecommerce.service.impl;

import com.sebadev.ecommerce.dto.ProductCategoryDto;
import com.sebadev.ecommerce.entity.ProductCategory;
import com.sebadev.ecommerce.repository.ProductCategoryRepository;
import com.sebadev.ecommerce.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    @Override
    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtos=productCategories.stream().map(this::mapToDto).collect(Collectors.toList());
        return productCategoryDtos;
    }

    private ProductCategoryDto mapToDto(ProductCategory productCategory){
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        productCategoryDto.setId(productCategory.getId());
        productCategoryDto.setCategoryName(productCategory.getCategoryName());
        return productCategoryDto;
    }

    private ProductCategory mapToEntity(ProductCategoryDto productCategoryDto){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productCategoryDto.getId());
        productCategory.setCategoryName(productCategoryDto.getCategoryName());
        return productCategory;
    }

}

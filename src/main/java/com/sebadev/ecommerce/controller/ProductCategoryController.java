package com.sebadev.ecommerce.controller;

import com.sebadev.ecommerce.dto.ProductCategoryDto;
import com.sebadev.ecommerce.service.ProductCategoryService;
import com.sebadev.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-category")
@CrossOrigin("http://localhost:4200")
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> getAllCategories(){
        List<ProductCategoryDto> productCategoriesDto= productCategoryService.getAllCategories();
        return ResponseEntity.ok(productCategoriesDto);
    }
}

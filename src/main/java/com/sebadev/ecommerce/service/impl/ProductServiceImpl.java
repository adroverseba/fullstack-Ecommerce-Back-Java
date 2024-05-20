package com.sebadev.ecommerce.service.impl;

import com.sebadev.ecommerce.dto.ProductDto;
import com.sebadev.ecommerce.dto.ProductResponse;
import com.sebadev.ecommerce.entity.Product;
import com.sebadev.ecommerce.entity.ProductCategory;
import com.sebadev.ecommerce.exception.ResourceNotFoundException;
import com.sebadev.ecommerce.repository.ProductCategoryRepository;
import com.sebadev.ecommerce.repository.ProductRepository;
import com.sebadev.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

//    @Override
//    public List<ProductDto> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        List<ProductDto> productsDto = products.stream().map(this::mapToDto).collect(Collectors.toList());
//        return productsDto;
//    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // create sort object
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Product> products = productRepository.findAll(pageable);

        //get content from the page object
        List<Product> listOfProducts= products.getContent();

        List<ProductDto> content = listOfProducts.stream().map(this::mapToDto).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        ProductCategory productCategory = productCategoryRepository.findById(
                productDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", productDto.getCategoryId())
        );

        Product product = mapToEntity(productDto);

        product.setCategory(productCategory);

        Product newProduct = productRepository.save(product);

        return mapToDto(newProduct);
    }

    @Override
    public ProductDto getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", id)
        );
        return mapToDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        ProductCategory existingProductCategory = productCategoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Product-Category", "id", productDto.getCategoryId()));

        existingProduct.setSku(productDto.getSku());
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setUnitPrice(productDto.getUnitPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.setActive(productDto.getActive());
        existingProduct.setUnitsInStock(productDto.getUnitsInStock());
        existingProduct.setCategory(existingProductCategory);

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToDto(updatedProduct);
    }

    @Override
    public void deleteProductById(long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductByCategory(long id,int pageNo,int pageSize) {
        if(!productCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category","id",id);
        }

        // create Pageable instance
        Pageable pageable = PageRequest.of( pageNo, pageSize);

        Page<Product> products= productRepository.findByCategoryId(id,pageable);

        List<Product> listOfProducts = products.getContent();

        List<ProductDto> content =listOfProducts.stream().map(this::mapToDto).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductByName(String name, int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        Page<Product> products= productRepository.findByNameContaining(name,pageable);
        List<Product> listOfProducts = products.getContent();
        List<ProductDto> content = listOfProducts.stream().map(this::mapToDto).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(content);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
    }

    //convert Entity into DTO
    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setUnitPrice(product.getUnitPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setActive(product.getActive());
        productDto.setUnitsInStock(product.getUnitsInStock());
        productDto.setDateCreated(product.getDateCreated());
        productDto.setLastUpdated(product.getLastUpdated());
        productDto.setCategoryId(product.getCategory().getId());
        return productDto;
    }

    //convert DTO into Entity
    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
//        product.setId(productDto.getId());
        product.setSku(productDto.getSku());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setActive(productDto.getActive());
        product.setUnitsInStock(productDto.getUnitsInStock());
        product.setDateCreated(productDto.getDateCreated());
        product.setLastUpdated(productDto.getLastUpdated());
//        product.setCategory(productDto.getCategoryId());

        return product;
    }
}
//private Long id;
//
//private String sku;
//
//private String name;
//
//private String description;
//
//private BigDecimal unitPrice;
//
//private String imageUrl;
//
//private boolean active;
//
//private int unitsInStock;
//
//private Date dateCreated;
//
//private Date lastUpdated;
//
//private ProductCategory categoryId;
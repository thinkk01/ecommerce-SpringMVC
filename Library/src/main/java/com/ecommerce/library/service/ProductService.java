package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    List<ProductDto> allProduct();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(MultipartFile imageProduct,ProductDto productDto);
    Optional<Product> findById(Long id);
    ProductDto getById(Long id);
    void deleteById(Long id);
    void enableById(Long id);
    // Customer
    List<Product> getAllProducts();
    List<ProductDto> products();
    List<Product> listViewProducts();
    Product getProductById(Long id);
    List<Product> getRelatedProducts(Long categoryId);
    List<Product> getAllProductInCategory(Long categoryId);
    List<Product> filterHighPrice();
    List<Product> filterLowPrice();
}

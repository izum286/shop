package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.CategoryDto;
import com.telran.telranshopspringdata.controller.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface AdminService {
    Optional<CategoryDto> addCategory(String categoryName);
    Optional<ProductDto> addProduct(String productName, BigDecimal price, String categoryId);
    boolean removeProduct(String productId);
    boolean removeCategory(String categoryId);
    boolean updateCategory(String categoryId, String categoryName);
    boolean changeProductPrice(String productId,BigDecimal price);
    boolean addBalance(String userEmail, BigDecimal balance);
}

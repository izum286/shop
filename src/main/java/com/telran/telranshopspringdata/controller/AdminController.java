package com.telran.telranshopspringdata.controller;

import com.telran.telranshopspringdata.controller.dto.CategoryDto;
import com.telran.telranshopspringdata.controller.dto.ProductDto;
import com.telran.telranshopspringdata.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("admin/category/{categoryName}")
    CategoryDto addCategory(@PathVariable("categoryName") String categoryName){
        return  adminService.addCategory(categoryName)
                .orElseThrow();
    }

    @PostMapping("admin/product")
    ProductDto addProduct(String productName, BigDecimal price, String categoryId){
        return adminService.addProduct(productName,price,categoryId).orElseThrow();
    }

    @DeleteMapping("product/{productId}")
    boolean removeProduct(@PathVariable("productId") String productId){
        return adminService.removeProduct(productId);
    }

    @DeleteMapping("category/{categoryId}")
    boolean removeCategory(@PathVariable("categoryId") String categoryId){
        return adminService.removeCategory(categoryId);
    }

    @PutMapping("category/{categoryId}/{categoryName}")
    boolean updateCategory(@PathVariable("categoryId") String categoryId, @PathVariable("categoryName") String categoryName){
        return adminService.updateCategory(categoryId, categoryName);
    }

    @PutMapping("product/{productId}/{price}")
    boolean changeProductPrice(@PathVariable ("producttId") String productId, @PathVariable("price") BigDecimal price){
        return adminService.changeProductPrice(productId, price);
    }

    @PutMapping("users/{userEmail}/{balance}")
    boolean addBalance(@PathVariable("userEmail") String userEmail, @PathVariable("balance") BigDecimal balance){
        return adminService.addBalance(userEmail, balance);
    }

}

package com.telran.telranshopspringdata.controller;


import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("user")
    public UserDto addUserInfo(@RequestBody UserDto user) {
        return service.addUserInfo(user.getEmail(), user.getName(), user.getPhone())
                .orElseThrow();
    }

    @GetMapping("user")
    public UserDto getUserInfo(Principal principal) {
        return service.getUserInfo(principal.getName())
                .orElseThrow();
    }

    @GetMapping("products")
    public List<ProductDto> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("categories")
    public List<CategoryDto> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("products/{categoryName}")
    public List<ProductDto> getProductByCategory(@PathVariable("categoryName") String categoryName) {
        return service.getProductsByCategory(categoryName);
    }

    @PostMapping("cart")
    public ShoppingCartDto addProductToCart(Principal principal,
                                            @RequestBody AddProductDto dto) {
        return service.addProductToCart(principal.getName(), dto.getProductId(), dto.getCount())
                .orElseThrow();
    }

    @GetMapping("cart")
    public ShoppingCartDto getShoppingCart(Principal principal) {
        return service.getShoppingCart(principal.getName())
                .orElseThrow();
    }

    @DeleteMapping("cart/{userEmail}/{productId}/{count}")
    public ShoppingCartDto removeProductFromCart(@PathVariable("userEmail") String userEmail,
                                                 @PathVariable("productId") String productId,
                                                 @PathVariable("count") int count) {
        return service.removeProductFromCart(userEmail,productId,count)
                .orElseThrow();
    }

    @DeleteMapping("cart/{userEmail}/all")
    public void clearShoppingCart(@PathVariable("userEmail") String userEmail) {
        service.clearShoppingCart(userEmail);
    }

    @GetMapping("orders")
    public List<OrderDto> getAllOrdersByEmail(Principal principal){
        return service.getOrders(principal.getName());
    }


    @GetMapping("checkout")
    public OrderDto checkout(Principal principal) {
        return service.checkout(principal.getName())
                .orElseThrow();
    }
}

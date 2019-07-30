package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.data.*;
import com.telran.telranshopspringdata.data.entity.ProductEntity;
import com.telran.telranshopspringdata.data.entity.ProductOrderEntity;
import com.telran.telranshopspringdata.data.entity.ShoppingCartEntity;
import com.telran.telranshopspringdata.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.telran.telranshopspringdata.service.Mapper.*;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Override
    //@Transactional
    public Optional<UserDto> addUserInfo(String email, String name, String phone) {
        if(!userRepository.existsById(email)){
            UserEntity entity = new UserEntity(email, name, phone, BigDecimal.valueOf(0), null, null);
            userRepository.save(entity);
            return Optional.of(map(entity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getUserInfo(String email) {
        UserEntity entity = userRepository.findById(email).orElseThrow();
        return Optional.of(map(entity));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(Mapper::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryId) {
        return productRepository.findAllByCategory_Id(categoryId)
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShoppingCartDto> addProductToCart(String userEmail, String productId, int count) {
        ShoppingCartEntity entity = shoppingCartRepository.findByOwner_Email(userEmail);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();

        if (entity != null ) {
            ProductOrderEntity poe = entity.getProducts().stream()
                    .filter(p -> p.getProductId().equals(productId))
                    .findAny()
                    .orElse(null);
            if (poe == null) {
                poe = new ProductOrderEntity();
                poe.setProductId(productEntity.getId());
                poe.setCategory(productEntity.getCategory());
                poe.setName(productEntity.getName());
                poe.setPrice(productEntity.getPrice());
                poe.setCount(count);
                productOrderRepository.save(poe);
                poe.setShoppingCart(entity);
                entity.getProducts().add(poe);
                shoppingCartRepository.save(entity);
            }else{
                poe.setCount(poe.getCount()+count);
            }
            return Optional.of(map(entity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShoppingCartDto> removeProductFromCart(String userEmail, String productId, int count) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByOwner_Email(userEmail);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        if (shoppingCartEntity.getProducts() != null) {
            ProductOrderEntity productOrderEntity =shoppingCartEntity.getProducts().stream().filter(p -> p.getProductId().equals(productId)).findAny()
            .orElse(null);
            if (productOrderEntity != null) {
                int productCount = productOrderEntity.getCount();
                if (productCount > count) {
                    productOrderEntity.setCount(productCount-count);
                }else {
                    shoppingCartEntity.getProducts().remove(productOrderEntity);
                }
                shoppingCartRepository.save(shoppingCartEntity);
            }else {
                throw new RuntimeException("No such product in cart");
            }
            return Optional.of(map(shoppingCartEntity));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShoppingCartDto> getShoppingCart(String userEmail) {
        return Optional.of(map(shoppingCartRepository.findByOwner_Email(userEmail)));
    }

    @Override
    public boolean clearShoppingCart(String userEmail) {
        return false;
    }

    @Override
    public List<OrderDto> getOrders(String userEmail) {
        return null;
    }

    @Override
    public Optional<OrderDto> checkout(String userEmail) {
        return Optional.empty();
    }
}

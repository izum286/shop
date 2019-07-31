package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.CategoryDto;
import com.telran.telranshopspringdata.controller.dto.ProductDto;
import com.telran.telranshopspringdata.data.CategoryRepository;
import com.telran.telranshopspringdata.data.ProductRepository;
import com.telran.telranshopspringdata.data.UserRepository;
import com.telran.telranshopspringdata.data.entity.CategoryEntity;
import com.telran.telranshopspringdata.data.entity.ProductEntity;
import com.telran.telranshopspringdata.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static com.telran.telranshopspringdata.service.Mapper.*;


@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdminServiceImpl implements AdminService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<CategoryDto> addCategory(String categoryName) {
        if (categoryRepository.getCategoryEntityByName(categoryName) == null) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(categoryName);
            categoryRepository.save(categoryEntity);
            return Optional.of(map(categoryEntity));
        }
        throw new RuntimeException("Category already exist");
    }

    @Override
    public Optional<ProductDto> addProduct(String productName, BigDecimal price, String categoryId) {
        if (productRepository.findByName(productName )==null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productName);
            productEntity.setPrice(price);
            productEntity.setCategory(categoryRepository.findById(categoryId).orElseThrow());
            productRepository.save(productEntity);
            return Optional.of(map(productEntity));
        }
        throw new RuntimeException("Product already exist");
    }

    @Override
    public boolean removeProduct(String productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCategory(String categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCategory(String categoryId, String categoryName) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        if (categoryEntity!=null) {
            categoryEntity.setName(categoryName);
            categoryRepository.save(categoryEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeProductPrice(String productId, BigDecimal price) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity!=null) {
            productEntity.setPrice(price);
            productRepository.save(productEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean addBalance(String userEmail, BigDecimal balance) {
        UserEntity userEntity = userRepository.findById(userEmail).orElse(null);
        if (userEntity!=null) {
            userEntity.setBalance(balance);
            userRepository.save(userEntity);
            return true;
        }
        return false;
    }
}

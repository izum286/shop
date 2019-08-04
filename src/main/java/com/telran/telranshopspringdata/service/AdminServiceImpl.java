package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.data.CategoryRepository;
import com.telran.telranshopspringdata.data.ProductRepository;
import com.telran.telranshopspringdata.data.UserRepository;
import com.telran.telranshopspringdata.data.entity.CategoryEntity;
import com.telran.telranshopspringdata.data.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdminServiceImpl implements AdminService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String addProduct(ProductDto productDto) {
        if (productRepository.findById(productDto.getId()) != null) {
            throw new RuntimeException("product already exist");
        }
        String categoryName = categoryRepository.getCategoryEntityByName(productDto.getCategory().getName()).getName();
        if (categoryName == null) {
            throw new RuntimeException("There is no suitable category for this product. ");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCategory(categoryRepository.getCategoryEntityByName(categoryName));
        productRepository.save(productEntity);
        return productEntity.getId();
    }

    @Override
    public String changeProductPrice(ChangeProductPriceDto changeProductPriceDto) {
        //вопрос: каков синтаксис чтобы при ткой форме записи RuntimeException принимал кастомное описание ошибки???
        ProductEntity productEntity = productRepository
                .findById(changeProductPriceDto.getProductId())
                .orElseThrow(RuntimeException::new);
        productEntity.setPrice(changeProductPriceDto.getPrice());
        productRepository.save(productEntity);
        return productEntity.getId() + " price changed";
    }

    @Override
    public String addCategory(CategoryDto categoryDto) {
        if (categoryRepository.getCategoryEntityByName(categoryDto.getName()) != null) {
            throw new RuntimeException("Category with name " + categoryDto.getName() + " already exist");
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId();
    }

    //===========statistic methods//===========statistic methods//===========statistic methods

    @Override
    public List<ProductStatisticDto> getMostPopularProduct() {
        return null;
    }

    @Override
    public List<ProductStatisticDto> getMostProfitableProduct() {
        return null;
    }

    @Override
    public List<UserStatisticDto> getMostActiveUser() {
        return null;
    }

    @Override
    public List<UserStatisticDto> getMostProfitableUser() {
        return null;
    }
}
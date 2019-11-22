package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.data.*;
import com.telran.telranshopspringdata.data.entity.CategoryEntity;
import com.telran.telranshopspringdata.data.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdminServiceImpl implements AdminService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStatisticRepository userStatisticRepository;

    @Autowired
    ProductStatisticRepository productStatisticRepository;



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

    /**
     * below - some methods for statistic representation
     * @return
     */


    @Override
    public List<ProductStatisticDto> getMostPopularProduct() {
        return productStatisticRepository.findFirstByNumberOfPurchaisesOrderByNumberOfPurchaisesDesc()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductStatisticDto> getMostProfitableProduct() {
        return productStatisticRepository.findFirstByTotalAmountOrderByTotalAmountDesc()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatisticDto> getMostActiveUser() {
        return userStatisticRepository.findFirstByTotalProductCountOrderByTotalProductCountDesc()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStatisticDto> getMostProfitableUser() {
        return userStatisticRepository.findFirstByTotalAmountOrderByTotalAmountDesc()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }






}
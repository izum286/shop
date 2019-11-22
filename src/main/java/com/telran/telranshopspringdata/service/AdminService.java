package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.controller.dto.*;

import java.util.List;

public interface AdminService {
    String addProduct(ProductDto productDto);
    String changeProductPrice(ChangeProductPriceDto changeProductPriceDto);
    String addCategory(CategoryDto categoryDto);

    List<ProductStatisticDto> getMostPopularProduct();
    List<ProductStatisticDto> getMostProfitableProduct();
    List<UserStatisticDto> getMostActiveUser();
    List<UserStatisticDto> getMostProfitableUser();
}

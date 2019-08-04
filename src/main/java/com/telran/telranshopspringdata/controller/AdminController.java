package com.telran.telranshopspringdata.controller;

import com.telran.telranshopspringdata.controller.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @PostMapping("product")
    public String addProduct(@RequestBody ProductDto dto){
        return null;
    }

    @PutMapping("product")
    public String changeProductPrice(@RequestBody ChangeProductPriceDto dto){
        return null;
    }

    @PostMapping("category")
    public String addCategory(@RequestBody CategoryDto dto){
        return null;
    }

    @GetMapping("statistic/mostPopularProducts")
    public List<ProductStatisticDto> getMostPopularProduct(){
        return null;
    }

    @GetMapping("statistic/mostProfitableProducts")
    public List<ProductStatisticDto> getMostProfitableProduct(){
        return null;
    }

    @GetMapping("statistic/mostActiveUser")
    public List<UserStatisticDto> getMostActiveUser(){
        return null;
    }

    @GetMapping("statistic/mostProfitableUser")
    public List<UserStatisticDto> getMostProfitableUser(){
        return null;
    }
}
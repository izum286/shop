package com.telran.telranshopspringdata.controller;

import com.telran.telranshopspringdata.controller.dto.*;
import com.telran.telranshopspringdata.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {


    @Autowired
    AdminService adminService;

    @PostMapping("product")
    //return id of added product
    public String addProduct(@RequestBody ProductDto dto){
        return adminService.addProduct(dto);
    }

    @PutMapping("product")
    public String changeProductPrice(@RequestBody ChangeProductPriceDto dto){
        return adminService.changeProductPrice(dto);
    }

    @PostMapping("category")
    public String addCategory(@RequestBody CategoryDto dto){
        return adminService.addCategory(dto);
    }
//===========statistic methods//===========statistic methods//===========statistic methods
    @GetMapping("statistic/mostPopularProducts")
    public List<ProductStatisticDto> getMostPopularProduct(){
        return null;
    }

    @GetMapping("statistic/mostProfitableProducts")
    public List<ProductStatisticDto>    getMostProfitableProduct(){
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
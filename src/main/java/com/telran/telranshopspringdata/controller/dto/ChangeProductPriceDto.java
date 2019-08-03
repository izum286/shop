package com.telran.telranshopspringdata.controller.dto;


import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChangeProductPriceDto {
    private String productId;
    private BigDecimal price;

}

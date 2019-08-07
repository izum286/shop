package com.telran.telranshopspringdata.data.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "user_stat")
public class UserStatisticEntity {
    @Id
    private String userEmail;
    //private List<ProductOrderDto> products;
    private int TotalProductCount;
    private BigDecimal totalAmount;
}

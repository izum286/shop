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
@Table(name = "product_stat")
public class ProductStatisticEntity {
    @Id
    private String productId;
    private String productName;
    private String productCategory;
    private int numberOfPurchaises;
    private BigDecimal totalAmount;
}

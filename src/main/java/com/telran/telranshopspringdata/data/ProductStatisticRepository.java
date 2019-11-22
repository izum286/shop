package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.ProductStatisticEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface ProductStatisticRepository extends CrudRepository<ProductStatisticEntity, String> {
    /**
     * lazy stream initialization - provide list of most popular products
     * @return
     */
    Stream<ProductStatisticEntity> findFirstByNumberOfPurchaisesOrderByNumberOfPurchaisesDesc();

    /**
     * lazy stream initialization - provide list of most profitable products
     * @return
     */
    Stream<ProductStatisticEntity>  findFirstByTotalAmountOrderByTotalAmountDesc();

}

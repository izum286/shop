package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.ProductStatistiEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface ProductStatisticRepository extends CrudRepository<ProductStatistiEntity, String> {
    //mostPopularProducts
    Stream<ProductStatistiEntity> findFirstByNumberOfPurchaisesOrderByNumberOfPurchaisesDesc();

    //mostProfitable
    Stream<ProductStatistiEntity>  findFirstByTotalAmountOrderByTotalAmountDesc();

}

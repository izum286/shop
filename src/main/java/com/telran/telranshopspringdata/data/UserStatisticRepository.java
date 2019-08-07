package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.UserStatisticEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface UserStatisticRepository extends CrudRepository<UserStatisticEntity, String> {

    //mostActiveUser
    Stream<UserStatisticEntity> findFirstByTotalProductCountOrderByTotalProductCountDesc();

    //mostProfitableUser
    Stream<UserStatisticEntity> findFirstByTotalAmountOrderByTotalAmountDesc();
}

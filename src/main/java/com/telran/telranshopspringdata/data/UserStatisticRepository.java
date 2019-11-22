package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.UserStatisticEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface UserStatisticRepository extends CrudRepository<UserStatisticEntity, String> {

    /**
     * lazy stream initialization - provide list of most active users
     * @return
     */
    Stream<UserStatisticEntity> findFirstByTotalProductCountOrderByTotalProductCountDesc();

    /**
     * lazy stream initialization - provide list of most profitable users
     * @return
     */
    Stream<UserStatisticEntity> findFirstByTotalAmountOrderByTotalAmountDesc();
}

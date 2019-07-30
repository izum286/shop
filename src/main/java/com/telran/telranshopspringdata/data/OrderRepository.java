package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface OrderRepository extends CrudRepository<OrderEntity, String>  {
    Stream<OrderEntity> getAllByOwnerEmail(String ownerEmail);
}

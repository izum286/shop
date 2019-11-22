package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;


public interface UserRepository extends CrudRepository<UserEntity, String> {
    /**
     * mock - delete from deploy
     * @return lazy stream of all users
     */
    Stream<UserEntity> getAllBy();
}

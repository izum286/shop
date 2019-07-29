package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

//не нужно помечать аннотацией bean тк все репо которые exstens spring repo - уже бин
public interface UserRepository extends CrudRepository<UserEntity, String> {
}

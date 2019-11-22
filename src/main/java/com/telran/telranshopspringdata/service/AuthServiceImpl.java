package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.data.UserDetailsRepository;
import com.telran.telranshopspringdata.data.entity.UserDetailsEntity;
import com.telran.telranshopspringdata.data.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@Service
@Transactional
public class AuthServiceImpl implements AuthService{
    @Autowired
    UserDetailsRepository repository;

   // @Autowired
    PasswordEncoder encoder;

    @Override
    public void registration(String email, String password) {
        if (repository.existsById(email)) {
            throw new RuntimeException("User Already Exist");
        }

        UserDetailsEntity entity = new UserDetailsEntity();
        entity.setEmail(email);
        entity.setPassword(encoder.encode(password));
        entity.setRoles(
                Arrays.asList(
                        UserRoleEntity.builder()
                        .role("ROLE_USER")
                        .build()
                )
        );
        repository.save(entity);
    }
}

package com.telran.telranshopspringdata.service;

import com.telran.telranshopspringdata.data.UserDetailsRepository;
import com.telran.telranshopspringdata.data.entity.UserDetailsEntity;
import com.telran.telranshopspringdata.data.entity.UserRoleEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

//тот кому я передам этот CustomUserDetailsService
//он будет лезть в репозиторий, и доставать оттуда юзеров
//потом будет создавать объект user котрый имплементирует в себя объект userDetails
//и возвра щать его authManager - у.
public class CustomUserDetailsService implements UserDetailsService {

    //инжектим через конструктор - хотя большой разницы нет
    UserDetailsRepository repository;
    public CustomUserDetailsService(UserDetailsRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsEntity entity = repository.findById(username).orElse(null);
        if ((entity==null)){
            throw new UsernameNotFoundException("user with email " + username + "not exist");
        }
        String[] roles = entity.getRoles().stream()
                .map(UserRoleEntity::getRole)
                .toArray(String[]::new);
        return new User(entity.getEmail(), entity.getPassword(), AuthorityUtils.createAuthorityList(roles));
    }
}

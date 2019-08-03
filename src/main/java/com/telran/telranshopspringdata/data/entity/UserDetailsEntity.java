package com.telran.telranshopspringdata.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {
    @Id
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_email", referencedColumnName = "email"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_role", referencedColumnName = "role"
            )
    )
    private List<UserRoleEntity> roles;

}

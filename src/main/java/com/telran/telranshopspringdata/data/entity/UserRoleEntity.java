package com.telran.telranshopspringdata.data.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
public class UserRoleEntity {

   @Id
   String role;
   @ManyToMany(mappedBy = "roles")
   private List<UserDetailsEntity> users;
}

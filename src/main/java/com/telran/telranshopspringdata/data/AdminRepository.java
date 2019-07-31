package com.telran.telranshopspringdata.data;

import com.telran.telranshopspringdata.data.entity.AdminEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity, String> {
}

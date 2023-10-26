package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findUserByDniAndPassword(String username, String password);
    User findUserByDni(String dni);
    User findUserByEmail(String email);
}

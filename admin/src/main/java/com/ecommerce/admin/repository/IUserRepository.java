package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username, String password);
}

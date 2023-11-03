package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User getUser(Long id);
    void createUser(User user);
    User validateUser(String username, String password);
    User findUserByDni(String dni);

    List<User> findAll();
    User findUserByEmail(String email);


}

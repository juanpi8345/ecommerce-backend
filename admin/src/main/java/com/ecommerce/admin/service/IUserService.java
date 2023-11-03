package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User getUser(Long id);
    void createUser(User user);
    User validateUser(String username, String password);
    User findUserByDni(String dni);

    void saveUser(User user);

    Page<User> findAllUserPaginated(int page, int pageSize, String sortField);
    List<User> findAll();
    User findUserByEmail(String email);

    List<User> searchUsers(String query);


}

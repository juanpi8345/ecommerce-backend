package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;
import com.ecommerce.admin.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepo;

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void createUser(User user) {
        user.setRol("USER");
        userRepo.save(user);
    }

    @Override
    public Optional<User> validateUser(String username, String password) {
        return Optional.of(userRepo.findUserByUsernameAndPassword(username,password));
    }


}

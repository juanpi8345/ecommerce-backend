package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;
import com.ecommerce.admin.repository.IUserRepository;
import org.mindrot.jbcrypt.BCrypt;
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
        user.setRole("USER");
        String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(password);
        userRepo.save(user);
    }

    @Override
    public User validateUser(String dni, String password) {
        User user = userRepo.findUserByDni(dni);
        if(BCrypt.checkpw(password, user.getPassword()) && user != null){
            return user;
        }
        return null;
    }

    @Override
    public User findUserByDni(String dni) {
        return userRepo.findUserByDni(dni);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }


}

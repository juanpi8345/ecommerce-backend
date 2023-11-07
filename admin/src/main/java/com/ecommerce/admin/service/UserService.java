package com.ecommerce.admin.service;

import com.ecommerce.admin.model.User;
import com.ecommerce.admin.repository.IUserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        user.setVerificated(false);
        user.setVerticationCode(this.generateCode());
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
    public String generateCode() {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 7; i++) {
            int randomIndex = random.nextInt(digits.length());
            char randomDigit = digits.charAt(randomIndex);
            code.append(randomDigit);
        }

        return code.toString();
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Page<User> findAllUserPaginated(int page, int pageSize, String sortField) {
        PageRequest pagerequest = PageRequest.of(page,pageSize, Sort.by(sortField));
        return userRepo.findAll(pagerequest);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public boolean verificateCode(String code) {
        return false;
    }

    @Override
    public List<User> searchUsers(String query) {
        return userRepo.findByDniContaining(query);
    }


}

package com.ecommerce.admin.controller;


import com.ecommerce.admin.dto.UserDTO;
import com.ecommerce.admin.model.User;
import com.ecommerce.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userServ;

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userServ.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
        Optional<User> user = userServ.validateUser(userDTO.getUsername(), userDTO.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}

package com.ecommerce.admin.controller;


import com.ecommerce.admin.dto.UserDTO;
import com.ecommerce.admin.model.User;
import com.ecommerce.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userServ;

    @GetMapping("/get")
    public List<User> getUsers(){
        return userServ.findAll();
    }

    @GetMapping("/{dni}/getRole")
    public Map<String,String> getUserRol(@PathVariable String dni){
        User user = userServ.findUserByDni(dni);
        Map<String,String> map = new HashMap<String,String>();
        if(user != null){
           map.put("Role",user.getRole());
           return map;
        }
        return null;

    }

    @GetMapping("/find/dni/{dni}")
    public ResponseEntity<User> findByDni(@PathVariable String dni){
        User user = userServ.findUserByDni(dni);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("exists/dni/{dni}")
    public boolean dniExists(@PathVariable String dni){
        User user = userServ.findUserByDni(dni);
        return user != null;
    }

    @GetMapping("exists/email/{email}")
    public boolean emailExists(@PathVariable String email){
        User user = userServ.findUserByEmail(email);
        return user != null;
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email){
        User user = userServ.findUserByEmail(email);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/register")
    public void register(@RequestBody User user){
        userServ.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
        Optional<User> user = Optional.ofNullable(userServ.validateUser(userDTO.getDni(), userDTO.getPassword()));
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

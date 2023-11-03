package com.ecommerce.admin.controller;


import com.ecommerce.admin.dto.UserDTO;
import com.ecommerce.admin.model.User;
import com.ecommerce.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<User> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(defaultValue = "dni") String sortField){
        return userServ.findAllUserPaginated(page,15,sortField);
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam("query") String query){
        return userServ.searchUsers(query);
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

    @PutMapping("/changeRole/user/{userId}")
    public void changeRoleToAdmin(@PathVariable Long userId){
        User user = userServ.getUser(userId);
        if(user != null){
            if(user.getRole().equals("USER"))
                user.setRole("ADMIN");
            else
                user.setRole("USER");
            userServ.saveUser(user);
        }

    }

}
